package qcc.rule;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;

import java.io.Serializable;

/**
 * 规则底座：定义所有规则的通用治理属性
 */
public abstract class RuleBaseObj implements Serializable {

    // 1. 核心属性
    public RuleType ruleType;             // 4大域分类
    public boolean governance = true;   // 是否需要治理地址签名验证
    public boolean gasPolicy = true;    // 是否需要支付 Gas (R因子生效)
    public RuleContinuousType ruleContinuousType; // 覆盖(Replace) 或 追加(Append)
    public RuleGovernanceType ruleGovernanceType; // 治理目标类型 (AssetId/Address等)
    public String handlerClass;         // 对应 Java 执行类的全限定名

    // 2. 规则元数据
    public String ruleName;             // 规则名称 (如: "AssetIssuance")
    public String version = "1.0.0";    // 规则版本

    /**
     * 计算当前规则交易需要的 Gas 费用
     * @param qccToUsdRate 当前 QCC 与 USD 的汇率 (100 QCC = 1 USD 时为 1.0)
     * @param rFactor 规则定义的 R 因子系数
     * @return 需要支付的 QCC 总量
     */
    public long calculateGas(double qccToUsdRate, int rFactor) {
        if (!gasPolicy) return 0;
        // 基准：100 QCC (对应 1 USD 参考值) * R因子 / 汇率
        return (long) ((100 * rFactor) / qccToUsdRate);
    }

    /**
     * 统一序列化为 JSON 字符串
     * 关键点：写入类名，以便反序列化识别具体子类
     */
    public String toJson() {
        // MapSortField 确保 Canonical JSON 的唯一性，用于哈希计算
        // WriteClassName 记录具体是 GasRuleObj 还是 AssetIssueRule
        return JSON.toJSONString(this,
                JSONWriter.Feature.WriteClassName,
                JSONWriter.Feature.MapSortField);
    }

    /**
     * 统一从 JSON 还原为具体对象
     */
    public static <T extends RuleBaseObj> T fromJson(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz, JSONReader.Feature.SupportAutoType);
    }
}