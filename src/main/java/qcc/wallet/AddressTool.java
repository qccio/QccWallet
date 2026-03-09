package qcc.wallet;

import org.bouncycastle.util.encoders.Hex;
import org.bouncycastle.crypto.digests.SHA3Digest;
import qcc.util.Base58;

import java.util.Arrays;

/**
 * QCC 地址生成工具 (服务端版)
 * 职责：验证 QccWallet 传来的公钥，计算出的地址是否与 claimed address 一致。
 * 算法： "Q" + Base58( Version(0x00) + RIPEMD160(SHA256(PubKey)) + Checksum )
 */
public class AddressTool {

    // QCC 主网版本号 (0x00)
    private static final byte QCC_VERSION_BYTE = 0x00;


    /**
     * 根据公钥 Hex 推导地址
     * @param publicKeyHex Dilithium5 公钥 Hex 字符串 (5184 chars)
     * @return Q开头的标准地址 (如: Q1B9fiSiPZ...)
     */
    public static String creatAddress(String publicKeyHex) {
        if (publicKeyHex == null || publicKeyHex.length() == 0) {
            return null;
        }
        try {
            // 0. Hex 解码 (得到 2592 字节的原始公钥)
            String cleanHex = publicKeyHex.replaceAll("[^0-9a-fA-F]", "");
            byte[] pubKeyBytes = Hex.decode(cleanHex);

            // 1. SHA3-256 (取代 SHA256 + RIPEMD160)
            SHA3Digest sha3Digest = new SHA3Digest(256);
            sha3Digest.update(pubKeyBytes, 0, pubKeyBytes.length);
            byte[] hash = new byte[32];
            sha3Digest.doFinal(hash, 0);
            byte[] hash20 = Arrays.copyOf(hash, 20); // 取前 20 字节作为地址核心
            // 2. 构建 Versioned Payload (1 byte version + 20 bytes hash)
            byte[] versionedPayload = new byte[21];
            versionedPayload[0] = QCC_VERSION_BYTE; // 0x00
            System.arraycopy(hash20, 0, versionedPayload, 1, 20);

            // 3. 计算 Checksum (Double SHA3-256)
            sha3Digest = new SHA3Digest(256);
            sha3Digest.update(versionedPayload, 0, versionedPayload.length);
            byte[] firstSHA = new byte[32];
            sha3Digest.doFinal(firstSHA, 0);

            sha3Digest = new SHA3Digest(256);
            sha3Digest.update(firstSHA, 0, firstSHA.length);
            byte[] secondSHA = new byte[32];
            sha3Digest.doFinal(secondSHA, 0);

            // 取前 4 字节作为校验和
            byte[] checksum = new byte[4];
            System.arraycopy(secondSHA, 0, checksum, 0, 4);

            // 4. 拼接最终二进制地址 [Version + Hash20 + Checksum] (共 25 bytes)
            byte[] binaryAddress = new byte[25];
            System.arraycopy(versionedPayload, 0, binaryAddress, 0, 21);
            System.arraycopy(checksum, 0, binaryAddress, 21, 4);

            // 5. Base58 编码并添加 "Q" 前缀
            String base58 = Base58.encode(binaryAddress);
            return "Q" + base58;

        } catch (Exception e) {
            System.err.println("[AddressTool] Error deriving address: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}