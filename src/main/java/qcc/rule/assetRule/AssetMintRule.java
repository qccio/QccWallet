package qcc.rule.assetRule;


import com.alibaba.fastjson2.JSON;

public class AssetMintRule {
    public String assetId;      // 目标资产ID
    public long amount;       // 本次铸造数量
    public String receiver;     // 接收地址
    public String proof;        // 铸造证明（可选）

    // 对接 RuleData
    public String toJson() { return JSON.toJSONString(this); }
    public static AssetMintRule fromJson(String json) {
        return JSON.parseObject(json, AssetMintRule.class);
    }
}