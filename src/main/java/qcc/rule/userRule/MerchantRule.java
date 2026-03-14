package qcc.rule.userRule;

import com.alibaba.fastjson2.JSON;
import qcc.rule.RuleBaseObj;

public class MerchantRule extends RuleBaseObj {
    public String address;
    public String assetId;
    public String credentialHash;
    // 开启后，所有 To 为该地址的交易，Gas 由该地址承担
    public boolean gasDelegation = true;

    public String toJson() { return JSON.toJSONString(this); }
    public byte[] toBytes() { return JSON.toJSONBytes(this); }
}