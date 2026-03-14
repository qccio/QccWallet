package qcc.wallet;

import org.bouncycastle.crypto.digests.SHA3Digest;
import org.bouncycastle.pqc.crypto.mldsa.*;
import org.bouncycastle.util.encoders.Hex;
import qcc.util.Base58;
import qcc.util.LogObj;
import java.security.SecureRandom;
import java.util.Arrays;

public class WalletUtil {
    private static final byte QCC_VERSION_BYTE = 0x00;
    private static final SecureRandom random = new SecureRandom();

    public static QccWallet createWallet() {
        // 1. 初始化 ML-DSA-87 生成器
        MLDSAKeyPairGenerator generator = new MLDSAKeyPairGenerator();
        generator.init(new MLDSAKeyGenerationParameters(random, MLDSAParameters.ml_dsa_87));

        // 2. 生成密钥对
        var kp = generator.generateKeyPair();
        MLDSAPublicKeyParameters pubKey = (MLDSAPublicKeyParameters) kp.getPublic();
        MLDSAPrivateKeyParameters privKey = (MLDSAPrivateKeyParameters) kp.getPrivate();

        // 3. 编码为 Hex
        // ML-DSA-87 标准：私钥 4896 字节, 公钥 2592 字节 (原始编码)
        String privHex = Hex.toHexString(privKey.getEncoded());
        String pubHex = Hex.toHexString(pubKey.getEncoded());

        // 4. 生成 Q 开头的地址
        String address = createAddress(pubHex);

        LogObj.println("ML-DSA-87 抗量子钱包生成成功！");
        LogObj.println("地址: " + address);
        LogObj.println("公钥长度: " + pubKey.getEncoded().length + " 字节");
        LogObj.println("私钥长度: " + privKey.getEncoded().length + " 字节");

        return new QccWallet(address, privHex, pubHex);
    }

    private static String createAddress(String publicKeyHex) {
        try {
            byte[] pubKeyBytes = Hex.decode(publicKeyHex.replaceAll("[^0-9a-fA-F]", ""));

            // 1. SHA3-256 哈希
            SHA3Digest sha3Digest = new SHA3Digest(256);
            sha3Digest.update(pubKeyBytes, 0, pubKeyBytes.length);
            byte[] hash = new byte[32];
            sha3Digest.doFinal(hash, 0);
            byte[] hash20 = Arrays.copyOf(hash, 20);

            // 2. Versioned Payload (0x00 + Hash20)
            byte[] payload = new byte[21];
            payload[0] = QCC_VERSION_BYTE;
            System.arraycopy(hash20, 0, payload, 1, 20);

            // 3. Double SHA3-256 Checksum
            byte[] checksum = calculateChecksum(payload);

            // 4. 拼接 [Version + Hash20 + Checksum] (25 bytes)
            byte[] binaryAddress = new byte[25];
            System.arraycopy(payload, 0, binaryAddress, 0, 21);
            System.arraycopy(checksum, 0, binaryAddress, 21, 4);

            return "Q" + Base58.encode(binaryAddress);
        } catch (Exception e) {
            return null;
        }
    }

    private static byte[] calculateChecksum(byte[] data) {
        SHA3Digest digest = new SHA3Digest(256);
        byte[] h1 = new byte[32];
        byte[] h2 = new byte[32];

        digest.update(data, 0, data.length);
        digest.doFinal(h1, 0);

        digest.update(h1, 0, h1.length);
        digest.doFinal(h2, 0);

        return Arrays.copyOf(h2, 4);
    }
}