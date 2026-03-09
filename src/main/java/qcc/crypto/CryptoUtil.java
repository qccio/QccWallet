package qcc.crypto;

import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
//区块，容器间数据哈希工具--可以上线使用

public class CryptoUtil {


    private static final int GCM_TAG_LENGTH = 16; // 16 bytes = 128 bits

    /** ==================== AES-GCM 加密 ==================== */
    public static byte[] aesGcmEncrypt(byte[] plain, byte[] key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        SecretKeySpec ks = new SecretKeySpec(key, "AES");
        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);
        cipher.init(Cipher.ENCRYPT_MODE, ks, spec);
        return cipher.doFinal(plain);
    }


    public static byte[] sha256(byte[]... inputs) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            for (byte[] input : inputs) {
                digest.update(input);
            }
            return digest.digest();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] sha256(byte[] input, int offset, int length) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(input, offset, length);
            return digest.digest();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // 单例 MessageDigest，避免每次 new（性能提升 5~10 倍）
    private static final ThreadLocal<MessageDigest> SHA3_256_THREAD_LOCAL = ThreadLocal.withInitial(() -> {
        try {
            return MessageDigest.getInstance("SHA3-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("你的JDK不支持 SHA3-256！请升级到 JDK 15+ 或添加 BouncyCastle 依赖", e);
        }
    });

    /**
     * 计算 SHA3-256 哈希（区块链最推荐的哈希算法）
     * 比 SHA-256 更安全、抗碰撞、更适合未来量子时代
     *
     * @param input 任意字节数组
     * @return 固定 32 字节的哈希值
     */
    public static byte[] sha3256(byte[] input) {
        if (input == null) {
            input = new byte[0];
        }
        MessageDigest digest = SHA3_256_THREAD_LOCAL.get();
        digest.update(input);
        return digest.digest(); // 每次 digest() 后会自动 reset，无需手动
    }

    public static byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i+1), 16));
        }
        return data;
    }


    // 加到你的 CryptoUtil.java 里
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * 计算 SHA3-256 并返回 16 进制字符串（用于打印、日志、调试）
     */
    public static String sha3256Hex(byte[] input) {
        return Hex.toHexString(sha3256(input));
    }

    public static String sha3256Hex(String input) {
        return Hex.toHexString(sha3256(input.getBytes()));
    }

    /**
     * 如果你暂时不想升级 JDK（比如还在用 JDK 8），
     * 下面是 BouncyCastle 版（推荐生产环境都加这个依赖，永不翻车）
     */
    public static byte[] sha3256WithBouncyCastle(byte[] input) {
        org.bouncycastle.jcajce.provider.digest.SHA3.DigestSHA3 digest =
                new org.bouncycastle.jcajce.provider.digest.SHA3.Digest256();
        digest.update(input);
        return digest.digest();
    }

    // 禁止实例化
    private CryptoUtil() {}

}