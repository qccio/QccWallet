package qcc.core.qccData;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.annotation.JSONField;
import com.alibaba.fastjson2.annotation.JSONType;
import qcc.rule.RuleType;
import qcc.util.LogObj;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * [优化版] 规则数据容器
 * 核心优化：二进制压缩、移除冗余哈希、对齐 ID 体系
 */
@JSONType(typeName = "rule")
public class RuleData extends QccBaseData {

    private static final String TYPE = "rule";

    @JSONField(ordinal = 7)
    public String ruleType;

    @JSONField(ordinal = 8)
    public String ruleJson;

    @JSONField(ordinal = 9)
    public String ruleName;

    @JSONField(ordinal = 10)
    public String ruleAppendJson;


    public RuleData() {
        super();
        this.assetId = "QCCRULE";
        initBaseFields();
    }

    @Override
    public String getType() { return TYPE; }

    @Override
    public byte getTypeCode() { return 2; } // 1:Tx, 2:Rule

    @Override
    public String getChain() {
        return LogObj.isDebug() ? "6666" : "9999";
    }

    // ====================== 1. 规则代码转换 (节省空间) ======================

    private byte getRuleTypeCode() {
        if (ruleType == null) return 2;
        try {
            return (byte) RuleType.valueOf(ruleType).ordinal();
        } catch (Exception e) {
            return 2;
        }
    }

    private static String getRuleTypeFromCode(byte code) {
        RuleType[] values = RuleType.values();
        if (code >= 0 && code < values.length) {
            return values[code].name();
        }
        return null;
    }

    // ====================== 2. 序列化与转换 (Binary) ======================

    @Override
    public byte[] toBytes() {
        // 🚀 优化：分配 64KB 缓冲区，确保 9-15 人多签不会溢出
        ByteBuffer buffer = ByteBuffer.allocate(64 * 1024);

        // 1. 写入公共基础字段
        writeBaseBytes(buffer);
        // 2. 写入 RuleData 特有字段
        buffer.put(getRuleTypeCode());
        writeString(buffer, ruleJson);
        writeString(buffer, ruleName);
        writeString(buffer, ruleAppendJson); // 🚀 必须写入，否则上链数据不全

        // 裁剪到实际大小
        byte[] result = new byte[buffer.position()];
        buffer.flip();
        buffer.get(result);
        return result;
    }

    public static RuleData fromBytes(byte[] data) {
        if (data == null) return null;
        return fromBytes(ByteBuffer.wrap(data));
    }

    public static RuleData fromBytes(ByteBuffer buffer) {
        try {
            RuleData rule = new RuleData();
            rule.readBaseBytes(buffer);
            rule.ruleType = getRuleTypeFromCode(buffer.get());
            rule.ruleJson = readString(buffer);
            rule.ruleName = readString(buffer);
            rule.ruleAppendJson = readString(buffer); // 🚀 必须读取

            return rule;
        } catch (Exception e) {
            LogObj.errprintln("❌ [RuleData] 二进制解析失败: " + e.getMessage());
            return null;
        }
    }

    // ====================== 3. 业务逻辑 ======================

    @Override
    public String getPayloadForHash() {
        // 🚀 ID 的根基：规则逻辑 + 时间戳
        return (ruleType != null ? ruleType : "") + (ruleJson != null ? ruleJson : "") + timestamp;
    }


    public String getTxHash() {
        if (this.id == null || this.id.isEmpty()) {
            finalizeId();
        }
        return this.id;
    }

    public static RuleData fromJson(String json) {
        if (json == null || json.isEmpty()) return null;
        try {
            return JSON.parseObject(json, RuleData.class);
        } catch (Exception e) {
            LogObj.errprintln("❌ [JSON解析失败] 格式不符合交易规范");
            return null;
        }
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }
}