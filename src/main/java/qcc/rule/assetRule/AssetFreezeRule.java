package qcc.rule.assetRule;

import com.alibaba.fastjson2.JSON;

public class AssetFreezeRule {
    public String assetId;
    public String targetAddress; // 要冻结的地址
    public boolean freeze;       // true为冻结，false为解冻
    public long expiry;          // 冻结有效期（0为永久）

    public String toJson() { return JSON.toJSONString(this); }
}