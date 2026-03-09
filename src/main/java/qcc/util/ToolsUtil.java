package qcc.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class ToolsUtil {

    private static final char[] BASE58 = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();

    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }


    public static void waitTimer(int sec){
        try {
            Thread.sleep(sec* 1000L); // 挂起 1 秒
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    public static String getbase64(String str) {
        try {
            return new String(Base64.getDecoder().decode(str.getBytes()));
        }  catch (Exception e) {
            return str;
        }
    }

    public static byte[] toByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }
    /** byte数组合并（前面 + 后面） */
    public static byte[] byteMerger(byte[] byte1, byte[] byte2) {
        if (byte1 == null && byte1.length == 0) return byte2;
        if (byte2 == null && byte2.length == 0) return byte1;
        byte[] result = new byte[byte1.length + byte2.length];
        System.arraycopy(byte1, 0, result, 0, byte1.length);
        System.arraycopy(byte2, 0, result, byte1.length, byte2.length);
        return result;
    }

    public static String getbase64(byte[] str) {
        return new String(Base64.getDecoder().decode(str));
    }
    public static String setbase64(byte[] data) {
        return new String(Base64.getEncoder().encode(data));
    }
    public static byte[] setbase64byte(byte[] data) {
        return Base64.getEncoder().encode(data);
    }

    public static String setbase64(String str) {
        //return  new String(Base64.encode(str.getBytes()),"UTF-8");
        //return new String(Base64.getEncoder().encode(str.toString().getBytes()),"UTF-8");
        return new String(Base64.getEncoder().encode(str.toString().getBytes()));
    }

    public static String base58Encode(byte[] input) {
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

    public static String random(int length) {
        // 可用字符集（数字 + 大小写字母）
        String CHAR_POOL =
                "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom RANDOM = new SecureRandom();

        if (length <= 0) {
            throw new IllegalArgumentException("length must be > 0");
        }

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(CHAR_POOL.length());
            sb.append(CHAR_POOL.charAt(index));
        }
        return sb.toString();
    }

    public static void waitTime(int intervalMs){
        try {
            Thread.sleep(intervalMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static long getRandomAmount(long min, long max) {
        return ThreadLocalRandom.current().nextLong(min, max + 1);
    }

}
