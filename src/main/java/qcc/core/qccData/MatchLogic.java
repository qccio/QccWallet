package qcc.core.qccData;

import com.alibaba.fastjson2.annotation.JSONField;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;

/**
 * [优化版] 现货/碰撞匹配逻辑
 * 核心优化：二进制指令化，解决数据膨胀
 */
public class MatchLogic {

    @JSONField(ordinal = 1)
    public String pairAssetId;   // 对价资产ID

    @JSONField(ordinal = 2)
    public double rate;          // 汇率

    @JSONField(ordinal = 3)
    public int maxParticipants;  // 总份数 (红包个数)

    @JSONField(ordinal = 4)
    public String rule;          // "random", "fixed", "exchange"

    @JSONField(ordinal = 5)
    public long remainingAmount; // 剩余金额 (由状态机更新)

    // ====================== 1. 二进制序列化 (解决数据膨胀) ======================

    /**
     * 🚀 极致优化：将规则字符串转为 1 字节代码
     */
    private byte getRuleCode() {
        if ("random".equals(rule)) return 1;
        if ("fixed".equals(rule)) return 2;
        if ("exchange".equals(rule)) return 3;
        return 0;
    }

    private static String getRuleFromCode(byte code) {
        switch (code) {
            case 1: return "random";
            case 2: return "fixed";
            case 3: return "exchange";
            default: return "unknown";
        }
    }

    /**
     * 序列化
     */
    public byte[] toBytes() {
        byte[] aidBytes = pairAssetId != null ? pairAssetId.getBytes(StandardCharsets.UTF_8) : new byte[0];

        // 长度：4(aidLen) + aid + 8(rate) + 4(maxP) + 1(ruleCode) + 8(remAmt)
        ByteBuffer buffer = ByteBuffer.allocate(25 + aidBytes.length);

        buffer.putInt(aidBytes.length);
        buffer.put(aidBytes);
        buffer.putDouble(rate);
        buffer.putInt(maxParticipants);
        buffer.put(getRuleCode()); // 🚀 字符串转字节，省去 10 字节以上空间
        buffer.putLong(remainingAmount);

        return buffer.array();
    }

    /**
     * 统一入口：从字节数组恢复
     */
    public static MatchLogic fromBytes(byte[] data) {
        if (data == null || data.length == 0) return null;
        return fromBytes(ByteBuffer.wrap(data));
    }

    public static MatchLogic fromBytes(ByteBuffer buffer) {
        try {
            MatchLogic logic = new MatchLogic();

            // 1. pairAssetId
            int aidLen = buffer.getInt();
            if (aidLen > 0) {
                byte[] aidB = new byte[aidLen];
                buffer.get(aidB);
                logic.pairAssetId = new String(aidB, StandardCharsets.UTF_8);
            }

            logic.rate = buffer.getDouble();
            logic.maxParticipants = buffer.getInt();

            // 2. rule (从字节码恢复字符串)
            logic.rule = getRuleFromCode(buffer.get());

            logic.remainingAmount = buffer.getLong();
            return logic;
        } catch (Exception e) {
            return null;
        }
    }

    // ====================== 2. 业务计算逻辑 (保持不变) ======================

    /**
     * [真随机核心] 碰撞时调用，计算单次发放金额
     */
    public long calculateCollisionWithCounter(long totalRemaining, int doneNum, int maxTotal) {
        if (doneNum >= maxTotal) return totalRemaining;

        int remainingPeople = maxTotal - doneNum + 1;
        if ("random".equals(rule)) {
            long avg = totalRemaining / remainingPeople;
            long offset = ThreadLocalRandom.current().nextLong(0, (avg / 2) + 1);
            long maxAllowed = avg + offset;
            long safetyLimit = totalRemaining - (remainingPeople - 1);
            long finalMax = Math.min(maxAllowed, safetyLimit);
            if (finalMax <= 1) return 1;
            return ThreadLocalRandom.current().nextLong(1, finalMax);

        } else if ("fixed".equals(rule)) {
            return totalRemaining / remainingPeople;
        }
        return 0;
    }
}