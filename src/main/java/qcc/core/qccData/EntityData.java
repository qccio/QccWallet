package qcc.core.qccData;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.annotation.JSONField;
import com.alibaba.fastjson2.annotation.JSONType;
import qcc.util.CryptoUtil;
import qcc.util.LogObj;
import java.nio.ByteBuffer;

/**
 * [单一载荷精简版] 实体数据交易结构
 * 优化：
 * 1. 移除冗余字段，复用基类 from 作为发布者。
 * 2. 载荷上限调整为 1MB。
 * 3. 纯粹二进制透传，适配跨语言开发（Java byte[] / Dart Uint8List / Swift Data）。
 */
@JSONType(typeName = "entity")
public class EntityData extends QccBaseData {

    private static final String TYPE = "entity";
    private static final int MAX_PAYLOAD_SIZE = 1024 * 1024; // 严格限制 1MB

    @JSONField(ordinal = 8)
    public byte[] payload;    // 唯一核心载荷

    public EntityData() {
        super();
        initBaseFields();
    }

    public EntityData(String assetId) {
        this();
        this.assetId = assetId;
    }

    @Override
    public String getTxHash() {
        if (this.id == null || this.id.isEmpty()) {
            finalizeId();
        }
        return this.id;
    }

    @Override
    public String getType() { return TYPE; }

    @Override
    public byte getTypeCode() { return 3; }

    @Override
    public String getChain() {
        return LogObj.isDebug() ? "6666" : "9999";
    }

    /**
     * 🚀 哈希计算逻辑：
     * 拼接基础字段 + 载荷的 SHA3-256 摘要，确保 ID 唯一性且不因大数据导致 OOM。
     */
    @Override
    public String getPayloadForHash() {
        String dataDigest = (payload != null && payload.length > 0)
                ? CryptoUtil.sha3256Hex(payload)
                : "empty";

        return (assetId != null ? assetId : "") + "|" +
                (from != null ? from : "") + "|" +
                dataDigest + "|" +
                timestamp;
    }

    // ====================== 序列化优化 (to/fromBytes) ======================

    @Override
    public byte[] toBytes() {
        int payloadLen = (payload != null) ? payload.length : 0;
        if (payloadLen > MAX_PAYLOAD_SIZE) {
            throw new RuntimeException("Entity payload exceeds 1MB limit!");
        }

        // 基础字段 + 4字节长度头 + 1MB 载荷
        ByteBuffer buffer = ByteBuffer.allocate(payloadLen + 512);

        // 1. 写入基类共有字段 (chain, id, assetId, from, timestamp, signatureHash)
        writeBaseBytes(buffer);

        // 2. 写入核心载荷
        if (payload != null) {
            buffer.putInt(payload.length);
            buffer.put(payload);
        } else {
            buffer.putInt(0);
        }

        byte[] result = new byte[buffer.position()];
        buffer.flip();
        buffer.get(result);
        return result;
    }

    public static EntityData fromBytes(byte[] data) {
        if (data == null || data.length == 0) return null;
        try {
            ByteBuffer buffer = ByteBuffer.wrap(data);
            EntityData entity = new EntityData();

            // 1. 读取基类共有字段
            entity.readBaseBytes(buffer);

            // 2. 读取载荷 (带边界检查)
            if (buffer.remaining() >= 4) {
                int payloadLen = buffer.getInt();
                if (payloadLen > 0 && payloadLen <= MAX_PAYLOAD_SIZE) {
                    if (buffer.remaining() >= payloadLen) {
                        entity.payload = new byte[payloadLen];
                        buffer.get(entity.payload);
                    }
                }
            }

            return entity;
        } catch (Exception e) {
            LogObj.errprintln("❌ [EntityData] 解析失败: " + e.getMessage());
            return null;
        }
    }

    public static EntityData fromJson(String json) {
        return JSON.parseObject(json, EntityData.class);
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }
}