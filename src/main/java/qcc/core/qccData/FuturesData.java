package qcc.core.qccData;

import com.alibaba.fastjson2.JSON;
import qcc.util.LogObj;
import java.nio.ByteBuffer;

public class FuturesData extends QccBaseData {
    private static final String TYPE = "futures";
    public FutureLogic futureLogic;
    public String parentTxHash;
    public long amount;
    public long unrealizedPnL = 0; // 当前浮动盈亏 (10^8)
    public long realizedPnL = 0;   // 已结算盈亏 (10^8)
    public long currentMargin = 0; // 动态保证金 (扣除亏损或增加盈利后的实时余额)
    public FuturesData() { super(); }

    @Override public String getType() { return TYPE; }
    @Override public byte getTypeCode() { return 4; } // 假设 2 为期货类型码
    @Override public String getChain() { return LogObj.isDebug() ? "6666" : "9999"; }

    @Override
    public String getPayloadForHash() {
        // 用于状态重放的核心载荷
        return chain + from + parentTxHash + assetId +
                (futureLogic != null ? (futureLogic.direction + "_" + futureLogic.entryPrice) : "");
    }

    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(2048);
        writeBaseBytes(buffer);
        writeString(buffer, parentTxHash);

        if (futureLogic != null) {
            buffer.put((byte) 1);
            byte[] flBytes = futureLogic.toBytes();
            buffer.putInt(flBytes.length);
            buffer.put(flBytes);
        } else {
            buffer.put((byte) 0);
        }

        byte[] result = new byte[buffer.position()];
        buffer.flip();
        buffer.get(result);
        return result;
    }

    // 第三方开发者接口：支持 JSON 解析
    public static FuturesData fromJson(String json) {
        try {
            return JSON.parseObject(json, FuturesData.class);
        } catch (Exception e) {
            LogObj.errprintln("❌ [FuturesData] JSON 解析失败");
            return null;
        }
    }

    public static FuturesData fromBytes(byte[] data) {
        ByteBuffer buffer = ByteBuffer.wrap(data);
        FuturesData fd = new FuturesData();
        fd.readBaseBytes(buffer);
        fd.parentTxHash = readString(buffer);

        if (buffer.get() == (byte) 1) {
            int len = buffer.getInt();
            fd.futureLogic = FutureLogic.fromBytes(buffer);
        }
        return fd;
    }

    @Override
    public String getTxHash() {
        if (this.id == null) finalizeId();
        return this.id;
    }
}