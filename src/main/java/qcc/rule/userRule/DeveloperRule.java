package qcc.rule.userRule;

import com.alibaba.fastjson2.JSON;
import qcc.rule.RuleBaseObj;

public class DeveloperRule extends RuleBaseObj {
    public String address;
    public String credentialHash;
    // 开启后，涉及该地址的有效交易将触发原子级 QCC 奖励
    public double rewardRate = 0.0001;

    public String toJson() { return JSON.toJSONString(this); }
    public byte[] toBytes() { return JSON.toJSONBytes(this); }
}