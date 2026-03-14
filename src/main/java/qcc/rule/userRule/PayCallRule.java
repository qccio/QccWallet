package qcc.rule.userRule;

import com.alibaba.fastjson2.JSON;
import qcc.rule.RuleBaseObj;

public class PayCallRule extends RuleBaseObj {
    public String address;         // 申请地址
    public String assetId;
    public String callUrl;         // 客户支付回调 URL
    public String credentialHash;  // 支付 Gas 费用的交易 Hash

    public String toJson() { return JSON.toJSONString(this); }
    public byte[] toBytes() { return JSON.toJSONBytes(this); }
    public static PayCallRule fromJson(String json) { return JSON.parseObject(json, PayCallRule.class); }


}