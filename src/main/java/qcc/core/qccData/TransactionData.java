package qcc.core.qccData;

import com.alibaba.fastjson2.JSON;
import qcc.util.LogObj;
import java.nio.ByteBuffer;

/**
 * 优化后的现货交易基础数据
 * - 取消了业务层 nonce，由通讯层 QccPacket 负责防重放
 * - 引入 timestamp 参与哈希计算，确保 ID 唯一性
 */
public class TransactionData extends QccBaseData {
    private static final String TYPE = "transaction";
    public String to;
    public long amount;
    public String memo;
    public String txType;
    public MatchLogic matchLogic;
    public String matcHash;
    public String fromdev;

    public TransactionData(String assetId) {
        super();
        this.assetId = assetId;
        initBaseFields();
    }

    public TransactionData() {
        super();
    }

    @Override
    public String getType() { return TYPE; }

    @Override
    public byte getTypeCode() { return 1; }

    @Override
    public String getChain() {
        return LogObj.isDebug() ? "6666" : "9999";
    }

    @Override
    public String getPayloadForHash() {
        // 【核心优化】取消 nonce，加入 timestamp 保证哈希唯一性
        // 载荷：chain + from + to + amount + timestamp + memo + assetId + matcHash
        String mHash = (matcHash == null || matcHash.isEmpty()) ? "" : matcHash;
        return chain + from + to + amount + timestamp + (memo != null ? memo : "") + assetId + mHash;
    }

    @Override
    public byte[] toBytes() {
        // 动态分配空间，由于取消了 nonce (8字节)，整体更紧凑
        ByteBuffer buffer = ByteBuffer.allocate(2048);
        // 1. 写入公共基础字段 (内部包含 timestamp, assetId, signatureHash 等)
        writeBaseBytes(buffer);
        // 2. 写入 Tx 特有业务字段 (移除 buffer.putLong(nonce))
        writeString(buffer, to);
        buffer.putLong(amount);
        writeString(buffer, memo);
        writeString(buffer, txType);
        writeString(buffer, fromdev);
        // 3. 写入 MatchLogic (采用 1 字节标识位)
        if (matchLogic != null) {
            buffer.put((byte) 1);
            byte[] mlB = matchLogic.toBytes();
            buffer.putInt(mlB.length);
            buffer.put(mlB);
        } else {
            buffer.put((byte) 0);
        }

        // 4. 写入 matcHash (1 字节标识位 + 字符串)
        if (matcHash != null && !matcHash.isEmpty()) {
            buffer.put((byte) 1);
            writeString(buffer, matcHash);
        } else {
            buffer.put((byte) 0);
        }

        byte[] result = new byte[buffer.position()];
        buffer.flip();
        buffer.get(result);
        return result;
    }

    public static TransactionData fromBytes(ByteBuffer buffer) {
        try {
            TransactionData tx = new TransactionData();
            // 1. 读取基础字段
            tx.readBaseBytes(buffer);

            // 2. 读取业务字段 (移除 tx.nonce = buffer.getLong())
            tx.to = readString(buffer);
            tx.amount = buffer.getLong();
            tx.memo = readString(buffer);
            tx.txType = readString(buffer);
            tx.fromdev = readString(buffer);

            // 3. 读取 MatchLogic
            if (buffer.get() == (byte) 1) {
                int mlLen = buffer.getInt();
                byte[] mlB = new byte[mlLen];
                buffer.get(mlB);
                tx.matchLogic = MatchLogic.fromBytes(mlB);
            }

            // 4. 读取 matcHash
            if (buffer.get() == (byte) 1) {
                tx.matcHash = readString(buffer);
            } else {
                tx.matcHash = null;
            }
            return tx;
        } catch (Exception e) {
            LogObj.errprintln("❌ [TransactionData] 二进制解析失败: " + e.getMessage());
            return null;
        }
    }

    // 其他工具方法保持不变...
    public static TransactionData fromJson(String json) {
        if (json == null || json.isEmpty()) return null;
        try {
            return JSON.parseObject(json, TransactionData.class);
        } catch (Exception e) {
            LogObj.errprintln("❌ [JSON解析失败] 格式不符合交易规范");
            return null;
        }
    }

    public String getTxHash() {
        if (this.id == null || this.id.isEmpty()) {
            finalizeId();
        }
        return this.id;
    }
}