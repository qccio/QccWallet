package qcc.core.qccData;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONException;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * 通讯层最小单元：客户端 -> 节点
 * 核心职责：承载数据、身份公钥、防重放机制
 */
public class QccPacket {

    // 客户端 Dilithium/Kyber 公钥 (Hex)
    public String publicKey;
    // 针对 payloadJson 的签名 (Hex)
    public String signature;
    // 核心业务数据 (TransactionData / RuleData 等的 JSON 字符串)
    public String payloadJson;
    // 防重放：客户端生成的时间戳 (毫秒)
    public long clientTime;
    // 防重放：随机数
    public long nonce;

    // --- 方法 ---

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public static QccPacket fromJson(String json) {
        try {
            return JSON.parseObject(json, QccPacket.class);
        } catch (JSONException e) {
            throw new RuntimeException("Packet format error", e);
        }
    }

    // --- 新增：二进制序列化 (To Byte) ---
    public byte[] toBytes() {
        byte[] pubKeyBytes = (publicKey != null) ? publicKey.getBytes(StandardCharsets.UTF_8) : new byte[0];
        byte[] sigBytes = (signature != null) ? signature.getBytes(StandardCharsets.UTF_8) : new byte[0];
        byte[] payloadBytes = (payloadJson != null) ? payloadJson.getBytes(StandardCharsets.UTF_8) : new byte[0];

        // 计算总长度：4字节(pubLen) + pubBytes + 4字节(sigLen) + sigBytes + 8字节(time) + 8字节(nonce) + 4字节(payLen) + payBytes
        int totalSize = 4 + pubKeyBytes.length + 4 + sigBytes.length + 8 + 8 + 4 + payloadBytes.length;

        ByteBuffer buffer = ByteBuffer.allocate(totalSize);

        buffer.putInt(pubKeyBytes.length);
        buffer.put(pubKeyBytes);

        buffer.putInt(sigBytes.length);
        buffer.put(sigBytes);

        buffer.putLong(clientTime);
        buffer.putLong(nonce);

        buffer.putInt(payloadBytes.length);
        buffer.put(payloadBytes);

        return buffer.array();
    }

    // --- 新增：二进制反序列化 (From Byte) ---
    public static QccPacket fromBytes(byte[] data) {
        if (data == null || data.length < 28) return null; // 最小长度校验

        ByteBuffer buffer = ByteBuffer.wrap(data);
        QccPacket packet = new QccPacket();

        // 读取 PublicKey
        int pubLen = buffer.getInt();
        byte[] pubBytes = new byte[pubLen];
        buffer.get(pubBytes);
        packet.publicKey = new String(pubBytes, StandardCharsets.UTF_8);

        // 读取 Signature
        int sigLen = buffer.getInt();
        byte[] sigBytes = new byte[sigLen];
        buffer.get(sigBytes);
        packet.signature = new String(sigBytes, StandardCharsets.UTF_8);

        // 读取时间戳和随机数
        packet.clientTime = buffer.getLong();
        packet.nonce = buffer.getLong();

        // 读取 Payload
        int payLen = buffer.getInt();
        byte[] payBytes = new byte[payLen];
        buffer.get(payBytes);
        packet.payloadJson = new String(payBytes, StandardCharsets.UTF_8);

        return packet;
    }
}
