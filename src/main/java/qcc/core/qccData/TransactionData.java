package qcc.core.qccData;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.annotation.JSONField;
import com.alibaba.fastjson2.annotation.JSONType;
import java.nio.ByteBuffer;

@JSONType(typeName = "transaction")
public class TransactionData extends QueBaseData {

    private static final String TYPE = "transaction";

    @JSONField(ordinal = 7)
    public String to;

    @JSONField(ordinal = 8)
    public long amount;

    @JSONField(ordinal = 9)
    public String memo;

    // 交易类型: "pay" (支付-高优先) 或 "tra" (转账-普通)
    @JSONField(ordinal = 10)
    public String txType;

    // 【新增】随机因子，确保每一笔交易 Hash 唯一
    @JSONField(ordinal = 11)
    public long nonce;

    public TransactionData() {
        super();
    }

    public TransactionData(String assetId) {
        initBaseFields(assetId);
        // 初始化时自动填充纳秒级随机数
        this.nonce = System.nanoTime();
    }

    public static TransactionData fromJson(String json) {
        return JSON.parseObject(json, TransactionData.class);
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public void setMemo(String memo) {
        if (memo != null && memo.length() > 128) {
            this.memo = memo.substring(0, 128); // 强制截断
            // 或者抛出异常阻止创建
        } else {
            this.memo = memo;
        }
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String getPayloadForHash() {
        return from + to + amount + assetId + nonce + timestamp;
    }

    /**
     * 【核心完善】将所有关键字段（含 nonce）序列化为字节流
     */
    @Override
    public byte[] toBytes() {
        // 增加容量以容纳 nonce
        ByteBuffer buffer = ByteBuffer.allocate(2048);
        // 1-7 继承自 QueBaseData 的字段
        writeString(buffer, type);         // 1. 类型
        writeString(buffer, id);           // 2. 唯一ID
        writeString(buffer, assetId);      // 3. 资产ID
        writeString(buffer, from);         // 4. 发送方
        buffer.putLong(timestamp);         // 5. 时间戳
        writeString(buffer, payloadHash);  // 6. 载荷哈希
        writeString(buffer, signatureHash);// 7. 签名哈希

        // 8-12 业务字段
        writeString(buffer, to);           // 8. 接收方
        buffer.putLong(amount);            // 9. 金额
        writeString(buffer, memo);         // 10. 备注
        writeString(buffer, txType);       // 11. 交易类型
        buffer.putLong(nonce);             // 12. 【关键】随机因子

        return buffer.array();
    }

    // 获取交易哈希的简便方法
    public String getTxHash() {
        return this.payloadHash;
    }
}