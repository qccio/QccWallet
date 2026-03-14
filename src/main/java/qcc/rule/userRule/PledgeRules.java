package qcc.rule.userRule;

import com.alibaba.fastjson2.JSON;
import qcc.rule.RuleBaseObj;


/**
 * 用户质押规则类
 * 涵盖节点质押与稳定币协议开通规则
 */
public class PledgeRules extends RuleBaseObj {

    // --- 质押阈值常量 ---

    /** 节点资源质押标准：10,000 QCC */
    private static final long NODE_RESOURCE_THRESHOLD = 10000000000000L;
    /** 稳定币协议开通质押标准：100,000 QCC */
    private static final long STABLECOIN_PROTOCOL_THRESHOLD = 100000000000000L;

    // --- 业务校验逻辑 ---
    public String address;
    public String assetId;
    public long amount;
    public String actionType;        // 动作类型: 1-质押-lock, 2-解押-unlock
    public String credentialHash;  // 支付 Gas 费用的交易 Hash
    public String protocol;
    public String pledgeJson;


    public String toJson() { return JSON.toJSONString(this); }
    public byte[] toBytes() { return JSON.toJSONBytes(this); }
    public static PayCallRule fromJson(String json) { return JSON.parseObject(json, PayCallRule.class); }


}