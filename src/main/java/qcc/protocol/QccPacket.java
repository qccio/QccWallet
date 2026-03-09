package qcc.protocol;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONException;

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
}
