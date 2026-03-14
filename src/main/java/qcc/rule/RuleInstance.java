package qcc.rule;

import com.alibaba.fastjson2.JSON;

import java.io.Serializable;
import java.util.Map;

/**
 * 规则实例：可实例化的规则参数，与规则 Api 对应
 * 这是规则交易的核心 Payload
 */
public class RuleInstance implements Serializable {

    public String ruleId;           // 规则唯一标识 (Hash)
    public RuleApiType apiType;     // 规则 API 类型 (决定 R 因子)
    public String targetKey;        // 治理目标 (AssetId / Address / Global)
    public RuleContinuousType mode; // 覆盖 Replace / 追加 append

    // 规则配置数据集：存储具体的参数，如 {"url": "http://api.com", "expire": 10000}
    public Map<String, Object> params;

    public String signature;        // 发布者的签名认证
    public long timestamp;          // 发布时间

    public RuleInstance() {
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 核心逻辑：从 JSON 还原并自动校验类型
     */
    public static RuleInstance fromJson(String json) {
        return JSON.parseObject(json, RuleInstance.class);
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    /**
     * 通用参数获取（推荐使用）
     */
    public Object getParam(String key) {
        return params != null ? params.get(key) : null;
    }

    public String getStringParam(String key) {
        Object val = getParam(key);
        return val != null ? String.valueOf(val) : null;
    }

    public long getLongParam(String key) {
        Object val = getParam(key);
        return val instanceof Number ? ((Number) val).longValue() : 0L;
    }

    /**
     * 计算本实例需要缴纳的 Gas 费用
     * @param gFactor 当前系统的 G-因子 (从 RuleManager 获取)
     * @param baseGas 规则基准费用 (通常为 100 QCC)
     */
    public double getRequiredGas(double gFactor, double baseGas) {
        if (apiType == null || apiType.domain == RuleType.GodRule) return 0.0;
        // 费用 = 基准 * R因子 * G调节因子
        return baseGas * apiType.rFactor * gFactor;
    }
}