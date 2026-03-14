package qcc.rule;

import com.alibaba.fastjson2.JSON;
import qcc.core.qccData.DataUtil;
import qcc.core.qccData.RuleData;
import qcc.rule.userRule.*;

public class RuleProcessor {

    public static void process(RuleData data) {
        // 1. 获取对应的子类类型
        Class<?> clazz = RuleRegistry.getRuleClass(data.ruleName);
        if (clazz == null) {
            System.err.println("❌ 未知的规则名称: " + data.ruleName);
            return;
        }

        // 2. 极速解析具体规则对象
        Object ruleObj = JSON.parseObject(data.ruleJson, clazz);

        // 3. 根据顶级类型进行分发
        if (DataUtil.isUserRule(data.ruleType)) {
            dispatchToUserManager(data.ruleName, ruleObj);
        } else if (DataUtil.isSystemRule(data.ruleType)) {
            // 处理 GasRule 或 RuleAddress 更新
        }
    }

    private static void dispatchToUserManager(String ruleName, Object obj) {
        UserRuleManager manager = UserRuleManager.getInstance();

        // 根据 ruleName 进行硬类型转换并分发至对应的存储空间
        switch (ruleName) {
            // 1. 支付回调规则：用于客户支付后的自动通知
            case "PayCallRule" -> {
                PayCallRule rule = (PayCallRule) obj;
                manager.payCallRules.put(rule.address, rule);
            }

            // 2. 商户规则：定义该地址为 Gas 代付商户
            case "MerchantRule" -> {
                MerchantRule rule = (MerchantRule) obj;
                manager.merchantRules.put(rule.address, rule);
            }

            // 3. 开发者规则：申请原子级开发奖励
            case "DeveloperRule" -> {
                DeveloperRule rule = (DeveloperRule) obj;
                manager.developerRules.put(rule.address, rule);
            }



            default -> System.err.println("⚠️ 未定义的 UserRule 类型: " + ruleName);
        }
    }
}