package qcc.core.qccData;

import com.alibaba.fastjson2.JSON;
import java.nio.ByteBuffer;

public class FutureLogic {
    public byte direction;    // 0: Short, 1: Long
    public int leverage;     // 杠杆倍数
    public long entryPrice;  // 开仓价 (放大 10^8)
    public long liqPrice;    // 爆仓线 (放大 10^8)
    public long stopPrice;   // 止损线 (放大 10^8)
    public long expireTime;  // 到期/有效时间 (0为永续)

    // 二进制序列化
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(64);
        buffer.put(direction);
        buffer.putInt(leverage);
        buffer.putLong(entryPrice);
        buffer.putLong(liqPrice);
        buffer.putLong(stopPrice);
        buffer.putLong(expireTime);
        byte[] result = new byte[buffer.position()];
        buffer.flip();
        buffer.get(result);
        return result;
    }

    public static FutureLogic fromBytes(ByteBuffer buffer) {
        FutureLogic logic = new FutureLogic();
        logic.direction = buffer.get();
        logic.leverage = buffer.getInt();
        logic.entryPrice = buffer.getLong();
        logic.liqPrice = buffer.getLong();
        logic.stopPrice = buffer.getLong();
        logic.expireTime = buffer.getLong();
        return logic;
    }
}