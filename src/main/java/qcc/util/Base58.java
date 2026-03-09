package qcc.util;
import java.math.BigInteger;
import java.util.Arrays;

public class Base58 {

    public static final char[] ALPHABET =
            "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();

    private static final int[] INDEXES = new int[128];

    static {
        Arrays.fill(INDEXES, -1);
        for (int i = 0; i < ALPHABET.length; i++) {
            INDEXES[ALPHABET[i]] = i;
        }
    }

    /**
     * Base58 解码（Bitcoin/Tron/QubeChain 标准）
     */
    public static byte[] decode(String input) {
        if (input.isEmpty()) {
            return new byte[0];
        }

        // 统计开头的 '1' → 代表前导零字节
        int zeros = 0;
        while (zeros < input.length() && input.charAt(zeros) == '1') {
            zeros++;
        }

        // Base58 → BigInteger
        BigInteger value = BigInteger.ZERO;
        for (int i = zeros; i < input.length(); i++) {
            char c = input.charAt(i);
            int digit = (c < 128) ? INDEXES[c] : -1;
            if (digit < 0) {
                throw new IllegalArgumentException("非法 Base58 字符: " + c);
            }
            value = value.multiply(BigInteger.valueOf(58)).add(BigInteger.valueOf(digit));
        }

        // BigInteger → byte[]
        byte[] decoded = value.toByteArray();

        // BigInteger 可能会产生一个多余的 0（正号），需要去掉
        if (decoded.length > 1 && decoded[0] == 0) {
            decoded = Arrays.copyOfRange(decoded, 1, decoded.length);
        }

        // 加回前导零（对应开头的 '1'）
        byte[] output = new byte[zeros + decoded.length];
        System.arraycopy(decoded, 0, output, zeros, decoded.length);

        return output;
    }

    /**
     * Base58 编码（可选）
     */
    public static String encode(byte[] input) {
        if (input.length == 0) {
            return "";
        }

        // Count leading zeros.
        int zeros = 0;
        while (zeros < input.length && input[zeros] == 0) {
            zeros++;
        }

        BigInteger value = new BigInteger(1, input);

        StringBuilder sb = new StringBuilder();
        while (value.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] divmod = value.divideAndRemainder(BigInteger.valueOf(58));
            value = divmod[0];
            sb.append(ALPHABET[divmod[1].intValue()]);
        }

        // Add leading '1' for each leading 0 byte.
        for (int i = 0; i < zeros; i++) {
            sb.append('1');
        }

        return sb.reverse().toString();
    }
}
