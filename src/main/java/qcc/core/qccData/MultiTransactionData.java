package qcc.core.qccData;

import com.alibaba.fastjson2.JSON;
import qcc.util.LogObj;
import java.nio.ByteBuffer;

/**
 * 优化后的多签交易数据模型
 * 支持并发交易追踪 (mutxHash) 与二进制紧凑序列化
 */
public class MultiTransactionData extends QccBaseData {
    private static final String TYPE = "multitransaction";

    public String fromMulti;    // 多签地址 (M开头)
    public String to;           // 接收地址
    public long amount;         // 金额
    public String memo;         // 备注 (可选)
    public String txType;       // 交易子类型 (pay, transfer, open, win)
    public MatchLogic matchLogic; // 撮合逻辑 (可选)
    public String matcHash;     // 撮合关联Hash (可选)
    public String mutxHash;     // 多签会话Hash：首笔交易的ID。为空表示创建，非空表示追加。
    public String mutxList;     //完成多签的其他交易单List 结构

    public MultiTransactionData() { super(); }

    @Override
    public String getType() { return TYPE; }

    @Override
    public byte getTypeCode() { return 4; } // 多签交易固定 Code 4

    @Override
    public String getChain() {
        return LogObj.isDebug() ? "6666" : "9999";
    }

    @Override
    public String getPayloadForHash() {
        // ID 生成载荷：必须包含 mutxHash 以区分并发交易
        return chain + from + fromMulti + to + amount + timestamp +
                (memo != null ? memo : "") + assetId +
                (mutxHash != null ? mutxHash : "");
    }

    /**
     * 二进制紧凑序列化
     */
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(2048);
        // 1. 写入公共基础字段 (来自 QccBaseData)
        writeBaseBytes(buffer);

        // 2. 核心业务字段
        writeString(buffer, fromMulti);
        writeString(buffer, to);
        buffer.putLong(amount);
        writeString(buffer, txType);
        buffer.putLong(timestamp);

        // 3. 可选字段标记位处理 (1表示有值, 0表示无)
        buffer.put((byte) (memo != null ? 1 : 0));
        if (memo != null) writeString(buffer, memo);

        buffer.put((byte) (mutxHash != null ? 1 : 0));
        if (mutxHash != null) writeString(buffer, mutxHash);

        buffer.put((byte) (matcHash != null ? 1 : 0));
        if (matcHash != null) writeString(buffer, matcHash);

        buffer.put((byte) (matchLogic != null ? 1 : 0));
        if (matchLogic != null) {
            byte[] mlB = matchLogic.toBytes();
            buffer.putInt(mlB.length);
            buffer.put(mlB);
        }

        byte[] result = new byte[buffer.position()];
        buffer.flip();
        buffer.get(result);
        return result;
    }

    public static MultiTransactionData fromBytes(byte[] data) {
        if (data == null) return null;
        return fromBytes(ByteBuffer.wrap(data));
    }

    public static MultiTransactionData fromBytes(ByteBuffer buffer) {
        try {
            MultiTransactionData tx = new MultiTransactionData();
            // 1. 读取基础字段
            tx.readBaseBytes(buffer);
            // 2. 读取业务字段
            tx.fromMulti = readString(buffer);
            tx.to = readString(buffer);
            tx.amount = buffer.getLong();
            tx.txType = readString(buffer);
            tx.timestamp = buffer.getLong();

            // 3. 读取可选字段
            if (buffer.get() == (byte) 1) tx.memo = readString(buffer);
            if (buffer.get() == (byte) 1) tx.mutxHash = readString(buffer);
            if (buffer.get() == (byte) 1) tx.matcHash = readString(buffer);

            if (buffer.get() == (byte) 1) {
                int mlLen = buffer.getInt();
                byte[] mlB = new byte[mlLen];
                buffer.get(mlB);
                tx.matchLogic = MatchLogic.fromBytes(mlB);
            }

            return tx;
        } catch (Exception e) {
            LogObj.errprintln("❌ [MultiTransactionData] 二进制解析失败: " + e.getMessage());
            return null;
        }
    }

    @Override
    public String getTxHash() {
        if (this.id == null || this.id.isEmpty()) {
            finalizeId();
        }
        return this.id;
    }

    public static MultiTransactionData fromJson(String json) {
        return JSON.parseObject(json, MultiTransactionData.class);
    }
}