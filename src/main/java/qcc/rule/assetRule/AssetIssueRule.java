package qcc.rule.assetRule;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import qcc.rule.RuleBaseObj;
import qcc.rule.RuleContinuousType;
import qcc.rule.RuleGovernanceType;
import qcc.rule.RuleType;

public class AssetIssueRule extends RuleBaseObj {
    public String assetId;      // 资产符号
    public String address;      // 合约/发行地址
    public String credentialHash;  //交易对应的凭证hash

    public int precision;       // 精度
    public String description;  // 描述
    public String totalSupply;  // 【关键】第一次发行的总量
    public boolean mintable;    // 是否允许后续增发
    public boolean freezable;   // 是否允许冻结

    public AssetIssueRule() {
        this.ruleType = RuleType.AsseIdRule;
        this.ruleContinuousType = RuleContinuousType.Replace;
        this.ruleGovernanceType = RuleGovernanceType.Asset;
        this.handlerClass = "AssetIssueRule";
        this.ruleName = "AssetIssuance";
        this.governance = true;
        this.address="";
    }

    public boolean isValid() {
        if (assetId == null || assetId.length() < 2) return false;
        if (precision < 0 || precision > 9) return false;
        try {
            if (totalSupply != null) new java.math.BigInteger(totalSupply);
        } catch (Exception e) { return false; }
        return true;
    }

    public byte[] toBytes() {
        return JSON.toJSONBytes(this, JSONWriter.Feature.WriteClassName, JSONWriter.Feature.FieldBased);
    }

    public static AssetIssueRule fromBytes(byte[] data) {
        return JSON.parseObject(data, AssetIssueRule.class,
                JSONReader.Feature.SupportAutoType,
                JSONReader.Feature.FieldBased);
    }
}