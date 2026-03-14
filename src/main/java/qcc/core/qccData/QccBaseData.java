package qcc.core.qccData;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.annotation.JSONField;
import com.alibaba.fastjson2.annotation.JSONType;
import qcc.util.CryptoUtil;
import qcc.util.ToolsUtil;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@JSONType(
        typeKey = "type",
        seeAlso = {RuleData.class, TransactionData.class,EntityData.class,FuturesData.class,MultiTransactionData.class}
)
public abstract class QccBaseData {

    @JSONField(ordinal = 0)
    public String chain;
    @JSONField(ordinal = 1)
    public String type;
    @JSONField(ordinal = 2)
    public String id;            // 这里的 ID = Content Hash (SHA3-256)
    @JSONField(ordinal = 3)
    public String assetId;
    @JSONField(ordinal = 4)
    public String from;
    @JSONField(ordinal = 5)
    public long timestamp;
    @JSONField(ordinal = 6)
    public String signatureHash; // 签名结果饿hash 签名内容太长

    protected void initBaseFields() {
        this.chain = getChain();
        this.type = getType();
        this.timestamp = System.currentTimeMillis();
        this.id = "";
    }

    public abstract String getTxHash();
    public abstract String getType();
    public abstract byte getTypeCode(); // 1:Tx, 2:Rule, 3:Entity
    public abstract String getChain();
    public abstract String getPayloadForHash();
    public abstract byte[] toBytes();
    // --- 核心工具：JSON 导出 ---
    public String toJson() {
        return JSON.toJSONString(this, JSONWriter.Feature.WriteMapNullValue);
    }

    /**
     * 🚀 核心：确定 ID (内容即 ID)
     */
    public void finalizeId() {
        this.id = CryptoUtil.sha3256Hex(getPayloadForHash());
    }

    // ====================== 二进制优化工具方法 ======================

    /**
     * 将 64 位十六进制哈希转为 32 字节原始字节写入
     */
    protected static void writeHash(ByteBuffer buffer, String hex) {
        if (hex == null || hex.length() < 64) {
            buffer.put(new byte[32]);
        } else {
            buffer.put(CryptoUtil.hexToBytes(hex));
        }
    }

    protected static String readHash(ByteBuffer buffer) {
        byte[] b = new byte[32];
        buffer.get(b);
        return CryptoUtil.bytesToHex(b);
    }

    protected static void writeString(ByteBuffer buffer, String str) {
        if (str == null) {
            buffer.putInt(0);
        } else {
            byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
            buffer.putInt(bytes.length);
            buffer.put(bytes);
        }
    }

    protected static String readString(ByteBuffer buffer) {
        int len = buffer.getInt();
        if (len <= 0) return "";
        byte[] bytes = new byte[len];
        buffer.get(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * 写入公共基础字节区
     */
    protected void writeBaseBytes(ByteBuffer buffer) {
        buffer.put(getTypeCode());      // 1 byte
        writeString(buffer, chain);     // len + string
        writeHash(buffer, id);          // 32 bytes (ID 即内容哈希)
        writeString(buffer, assetId);   // len + string
        writeString(buffer, from);      // 地址通常较长，保留 String 格式或长度前缀字节
        buffer.putLong(timestamp);      // 8 bytes
        writeString(buffer, signatureHash); // 签名较大且变长
    }

    protected void readBaseBytes(ByteBuffer buffer) {
        buffer.get(); // 跳过 typeCode (由外部工厂或子类处理)
        this.chain = readString(buffer);
        this.id = readHash(buffer);
        this.assetId = readString(buffer);
        this.from = readString(buffer);
        this.timestamp = buffer.getLong();
        this.signatureHash = readString(buffer);
    }
}