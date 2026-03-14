package qcc.rule;

import qcc.rule.qccRule.GasRuleObj;
import qcc.rule.qccRule.RuleAddress;
import qcc.rule.userRule.DeveloperRule;
import qcc.rule.userRule.MerchantRule;
import qcc.rule.userRule.MultiSignature;
import qcc.rule.userRule.PayCallRule;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 规则工厂：用于快速将 ruleName 映射到具体的业务类
 */
public class RuleRegistry {
    private static final ConcurrentHashMap<String, Class<?>> REGISTRY = new ConcurrentHashMap<>();

    static {
        // 系统级规则注册
        REGISTRY.put("GasRule", GasRuleObj.class);
        REGISTRY.put("RuleAddressManager", RuleAddress.class);

        // 用户级规则注册
        REGISTRY.put("PayCallRule", PayCallRule.class);
        REGISTRY.put("MerchantRule", MerchantRule.class);
        REGISTRY.put("DeveloperRule", DeveloperRule.class);
        REGISTRY.put("MultiSignature", MultiSignature.class);
    }

    public static Class<?> getRuleClass(String ruleName) {
        return REGISTRY.get(ruleName);
    }
}