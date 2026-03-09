package qcc.wallet;

import org.bouncycastle.crypto.digests.SHA3Digest;
import org.bouncycastle.pqc.crypto.crystals.dilithium.*;
import org.bouncycastle.util.encoders.Hex;
import qcc.crypto.CryptoUtil;
import qcc.util.Base58;
import qcc.util.LogObj;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;

public class QccWallet {
    public final String address;       // Q开头Base58地址
    public String privateKeyHex;   //请注意，这是测试采用public 建议在生产或实际开发private
    public final String publicKeyHex;   //公钥匙必须返回到调用服务器
    private static final DilithiumParameters PARAMS = DilithiumParameters.dilithium5;
    public QccWallet(String address, String privateKey, String publicKey) {
        this.address = address;

        this.privateKeyHex = privateKey;
        this.publicKeyHex = publicKey;

    }
    public static QccWallet creatQccWallet(){
        return WalletUtil.createWallet();
    }
    public DilithiumPrivateKeyParameters getPrivateObj(){
        DilithiumPrivateKeyParameters privKey = DilithiumKeyRestorer.restoreRawPrivateKey(privateKeyHex);
        return privKey;
    }
    public DilithiumPublicKeyParameters getPubObj(){
        byte[] pubBytes = Hex.decode(publicKeyHex);
        DilithiumPublicKeyParameters pubKey = new DilithiumPublicKeyParameters(PARAMS, pubBytes);
        return pubKey;
    }
    // 签名（终极能跑版！！！直接用内存私钥对象）
    public String signString(String message) {
        DilithiumSigner signer = new DilithiumSigner();
        signer.init(true, getPrivateObj());  // true = 签名模式
        byte[] signature = signer.generateSignature(message.getBytes());
        String signatureHex = Hex.toHexString(signature);
        return signatureHex;
    }
    public byte[] signByte(byte[] data) {
        DilithiumSigner signer = new DilithiumSigner();
        signer.init(true, getPrivateObj());
        return signer.generateSignature(data);  // 直接签原始字节！最硬！
    }
    // 验证字符串
    public boolean verifyString(String message, String signatureHex) {
        DilithiumSigner verifier = new DilithiumSigner();
        verifier.init(false, getPubObj());
        return verifier.verifySignature(
                message.getBytes(),
                Hex.decode(signatureHex)
        );
    }
    // ==================== 支持原始公钥字节的 verify（推荐！） ====================
    public boolean verifyByte(byte[] message, byte[] signature) {
        DilithiumSigner verifier = new DilithiumSigner();
        verifier.init(false, getPubObj());
        return verifier.verifySignature(
                message,
                signature
        );
    }
    public void destroy() {
        // 1. 清除 privateKeyHex 字符串（通过反射或覆盖字符）
        if (privateKeyHex != null) {
            privateKeyHex="";
        }
        // 提示 GC 尽快回收（建议但非必须）
        System.gc(); // 仅建议，不保证立即执行
    }
    public int getPrileng(){
        return privateKeyHex.length();
    }
    public String toHash()  {
       String data=address+privateKeyHex+publicKeyHex;
       try {
           return CryptoUtil.bytesToHex(CryptoUtil.sha3256(data.getBytes("UTF-8")));
       } catch (UnsupportedEncodingException e) {
           return "";
       }
    }
}


class DilithiumKeyRestorer {
    // 您的环境特有的组件长度常量
    private static final int RHO_LEN = 32;
    private static final int K_LEN = 32;
    private static final int TR_LEN = 64;
    private static final int S1_LEN = 672;
    private static final int S2_LEN = 768;
    private static final int T0_LEN = 3328;
    private static final int T1_LEN = 2560;
    // 假设您的 QubeChain 使用 Dilithium5 (或对应的自定义参数)
    private static final DilithiumParameters QUBE_DILITHIUM_PARAMS = DilithiumParameters.dilithium5;
    /**
     * 【Dilithium 私钥还原】
     * 从 Hex 编码的原始字节数组 (7456 字节) 中精确还原私钥对象。
     * * @param privateKeyHex 7456 字节原始私钥的 Hex 编码字符串。
     * @return 可用的 DilithiumPrivateKeyParameters 对象。
     */
    public static DilithiumPrivateKeyParameters restoreRawPrivateKey(String privateKeyHex) {
        // 1. 解码 Hex 得到 7456 字节的原始私钥
        byte[] rawKeyBytes = Hex.decode(privateKeyHex);
        if (rawKeyBytes.length != 7456) {
            throw new IllegalArgumentException("Raw key length is incorrect. Expected 7456 bytes, got " + rawKeyBytes.length);
        }
        // 2. 使用 ByteBuffer 或偏移量进行精确分割
        ByteBuffer buffer = ByteBuffer.wrap(rawKeyBytes);
        byte[] rho = extractBytes(buffer, RHO_LEN);
        byte[] k = extractBytes(buffer, K_LEN);
        byte[] tr = extractBytes(buffer, TR_LEN);
        byte[] s1 = extractBytes(buffer, S1_LEN);
        byte[] s2 = extractBytes(buffer, S2_LEN);
        byte[] t0 = extractBytes(buffer, T0_LEN);
        byte[] t1 = extractBytes(buffer, T1_LEN); // 剩余部分
        // 3. 使用七参数构造函数还原对象
        return new DilithiumPrivateKeyParameters(
                QUBE_DILITHIUM_PARAMS, rho, k, tr, s1, s2, t0, t1);
    }
    /**
     * 辅助方法：从 ByteBuffer 中提取指定长度的字节数组。
     */
    private static byte[] extractBytes(ByteBuffer buffer, int length) {
        byte[] bytes = new byte[length];
        buffer.get(bytes);
        return bytes;
    }
}
class WalletUtil {
    private static final byte QCC_VERSION_BYTE = 0x00;
    private static final SecureRandom random = new SecureRandom();
    private static final char[] BASE58 = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();
    //创建钱包
    public static QccWallet createWallet() {
        DilithiumKeyPairGenerator generator = new DilithiumKeyPairGenerator();
        generator.init(new DilithiumKeyGenerationParameters(random, DilithiumParameters.dilithium5));
        var kp = generator.generateKeyPair();

        DilithiumPublicKeyParameters pubKey = (DilithiumPublicKeyParameters) kp.getPublic();
        DilithiumPrivateKeyParameters privKey = (DilithiumPrivateKeyParameters) kp.getPrivate();
        LogObj.println("你真正使用的参数是: " + privKey.getParameters().getName());
        LogObj.println("私钥编码长度: " + privKey.getEncoded().length);
        LogObj.println("参数名: " + privKey.getParameters().getName());
        String privHex= Hex.toHexString(getRawPrivateKeyBytes(privKey));  //私钥转换方式，千万不要修改，目前测试 ok
        String pubHex = Hex.toHexString(pubKey.getEncoded());
        String address=creatAddress(pubHex);
        LogObj.println("抗量子钱包生成成功！");
        LogObj.println("公钥匙长度: " + pubHex.length() + ")字节长度:"+pubKey.getEncoded().length);
        LogObj.println("地址: " + address + " (长度: " + address.length() + ")");
        LogObj.println("私钥长度: " + privHex.length() + "... (测试阶段打印，上线必须加密私钥长度:)"+ privKey.getEncoded().length);
        return new QccWallet(address, privHex, pubHex);
    }
    /**
     * 根据公钥 Hex 推导地址
     * @param publicKeyHex Dilithium5 公钥 Hex 字符串 (5184 chars)
     * @return Q开头的标准地址 (如: Q1B9fiSiPZ...)
     */
    private static String creatAddress(String publicKeyHex) {
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
    static byte[] getRawPrivateKeyBytes(DilithiumPrivateKeyParameters privKey) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            baos.write(privKey.getRho());
            baos.write(privKey.getK());
            baos.write(privKey.getTr());
            baos.write(privKey.getS1());
            baos.write(privKey.getS2());
            baos.write(privKey.getT0());
            baos.write(privKey.getT1());
            byte[] rawKey = baos.toByteArray();
            LogObj.println("【正确的原始私钥长度】: " + rawKey.length);
            return rawKey; // 存储这个 byte[] 的 Hex 串
        } catch (IOException e) {
            throw new RuntimeException("Failed to concatenate Dilithium key components.", e);
        }
    }
    // 工具方法
    private static byte[] sha256(byte[] input) {
        try {
            return MessageDigest.getInstance("SHA-256").digest(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] ripemd160(byte[] input) {
        try {
            return org.bouncycastle.jcajce.provider.digest.RIPEMD160.Digest.getInstance("RIPEMD160").digest(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static String base58Encode(byte[] input) {
        // 经典Base58实现（比特币/TRON同款）
        StringBuilder sb = new StringBuilder();
        int zeros = 0;
        for (byte b : input) if (b == 0) zeros++; else break;
        BigInteger num = new BigInteger(1, input);
        while (num.compareTo(BigInteger.ZERO) > 0) {
            int remainder = num.mod(BigInteger.valueOf(58)).intValue();
            sb.insert(0, BASE58[remainder]);
            num = num.divide(BigInteger.valueOf(58));
        }
        for (int i = 0; i < zeros; i++) sb.insert(0, BASE58[0]);
        return sb.toString();
    }
}

//请问这个类是否实现抗量子威胁和量子计算机可以通过公钥解密私钥