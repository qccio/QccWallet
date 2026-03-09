package qcc.util;

import java.time.Instant;

/**
 * 时间戳编码工具类
 * 用于区块、容器、交易等统一时间存储
 * 优势：可读性 + 节约存储空间
 */
public class TimestampUtil {

    // 容器启动基准时间，可按容器初始化时设置
    private static long BASE_TIME = Instant.now().toEpochMilli();

    // 高进制字符集（32进制：0-9 + A-V）
    private static final char[] CHARSET = "0123456789ABCDEFGHIJKLMNOPQRSTUV".toCharArray();
    private static final int BASE = CHARSET.length;

    /**
     * 设置容器基准时间
     * @param baseTimeMillis 容器启动时间，单位毫秒
     */
    public static void setBaseTime(long baseTimeMillis) {
        BASE_TIME = baseTimeMillis;
    }

    /**
     * 编码时间戳
     * @param currentTimeMillis 当前时间毫秒
     * @return 高进制编码字符串
     */
    public static String encode(long currentTimeMillis) {
        long delta = currentTimeMillis - BASE_TIME;
        return toBase32(delta);
    }

    /**
     * 解码时间戳
     * @param encoded 高进制编码字符串
     * @return 绝对时间毫秒
     */
    public static long decode(String encoded) {
        long delta = fromBase32(encoded);
        return BASE_TIME + delta;
    }

    // ===================== 内部方法 =====================

    // 转32进制编码
    private static String toBase32(long value) {
        if (value == 0) return "0";
        StringBuilder sb = new StringBuilder();
        long v = value;
        while (v > 0) {
            sb.append(CHARSET[(int)(v % BASE)]);
            v /= BASE;
        }
        return sb.reverse().toString();
    }

    // 从32进制解码
    private static long fromBase32(String str) {
        long result = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            int index = c <= '9' ? c - '0' : c - 'A' + 10;
            result = result * BASE + index;
        }
        return result;
    }


}
