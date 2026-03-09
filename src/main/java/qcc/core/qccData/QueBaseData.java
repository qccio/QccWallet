package qcc.core.qccData;

import com.alibaba.fastjson2.annotation.JSONField;
import com.alibaba.fastjson2.annotation.JSONType;
import qcc.util.ToolsUtil;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

// 加上这个注解！！！
@JSONType(
        typeKey = "type",
        seeAlso = {EntityData.class, RuleData.class, TransactionData.class}
)


public abstract class QueBaseData {

    @JSONField(ordinal = 0)
    public String type;

    @JSONField(ordinal = 1)
    public String id;

    @JSONField(ordinal = 2)
    public String assetId;

    @JSONField(ordinal = 3)
    public String from;

    @JSONField(ordinal = 4)
    public long timestamp;

    // 主网字段（测试/主网都一样）
    @JSONField(ordinal = 5)
    public String payloadHash;

    @JSONField(ordinal = 6)
    public String signatureHash;

    protected void initBaseFields(String assetId) {
        this.type=getType();
        this.id = ToolsUtil.getUUID();
        this.assetId = assetId;
        this.timestamp = System.currentTimeMillis();
    }

    public abstract String getType();

    // 用于主网签名的原始内容（子类提供）
    public abstract String getPayloadForHash();

    public void finalizeForContainerWrite() {
        if (payloadHash == null || signatureHash == null) {
            throw new RuntimeException("payloadHash / signatureHash 未生成，拒绝写入容器");
        }
        // debug 字段不写入容器，但 JSON 层保留
    }

    // --- 序列化辅助工具 ---

    protected static byte[] strToBytes(String str) {
        return (str == null ? "" : str).getBytes(StandardCharsets.UTF_8);
    }

    protected static void writeString(ByteBuffer buffer, String str) {
        byte[] bytes = strToBytes(str);
        buffer.putInt(bytes.length);
        buffer.put(bytes);
    }

    protected static String readString(ByteBuffer buffer) {
        int len = buffer.getInt();
        byte[] bytes = new byte[len];
        buffer.get(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * 定义抽象方法，由子类实现具体的全包字节化
     */
    public abstract byte[] toBytes();
}
