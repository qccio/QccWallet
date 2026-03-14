package qcc.core.qccData;

import com.alibaba.fastjson2.JSON;
import qcc.rule.RuleType;
import qcc.util.CryptoUtil;

public class DataUtil {

    public static boolean isPayment(String txType){
        return txType.equals(TranType.pay.name());
    }
    public static boolean isWin(String txType){
        return txType.equals(TranType.win.name());
    }
    public static boolean isOpen(String txType){
        return txType.equals(TranType.open.name());
    }

    public static String preCheck(TransactionData tx) {
        // 1. 基础字段非空检查
        if (tx.assetId == null || tx.assetId.isEmpty()) return "Missing AssetID";
        if (tx.from == null || tx.from.isEmpty()) return "Missing Sender";
        if (tx.to == null || tx.to.isEmpty()) return "Missing Receiver";
        // 2. 类型检查 (严格按照 TranType 枚举定义检查)
        boolean isValidType = false;
        if (tx.txType != null) {
            for (TranType type : TranType.values()) {
                if (type.name().equals(tx.txType)) {
                    isValidType = true;
                    break;
                }
            }
        }
        if (!isValidType) {
            StringBuilder sb = new StringBuilder("Invalid txType: [");
            sb.append(tx.txType).append("]. Must be one of: ");
            for (TranType t : TranType.values()) sb.append(t.name()).append(" ");
            return sb.toString().trim();
        }
        // 3. 特殊黑名单规则
        if ("FRE-0".equalsIgnoreCase(tx.from)) return "Rejected: Sender is FRE-0";
        if ("FRE-0".equalsIgnoreCase(tx.to)) return "Rejected: Receiver is FRE-0";
        // 4. 金额规则
        if (tx.amount <= 0 && !DataUtil.isWin(tx.txType)) return "Rejected: Amount must be positive";
        if (tx.txType.equals(TranType.open.name()) && tx.matchLogic==null){
            return " open must matchLogic err";
        }
        if (tx.txType.equals(TranType.win.name()) && tx.matcHash==null){
            return " win must matchHash err";
        }
        if (tx.txType.equals(TranType.win.name()) && tx.matcHash.isEmpty()){
            return " win must matchHash err";
        }
        if(tx.txType.equals(TranType.win.name())){
            if(!CryptoUtil.isHex(tx.matcHash)){
                return " win must matchHash err";
            }
        }
        return null; // 校验通过
    }

    public static boolean isGodRule(String ruleType) {
        return RuleType.GodRule.name().equals(ruleType);
    }
    public static  boolean isSystemRule(String ruleType) {

        return RuleType.SystemRule.name().equals(ruleType);
    }

    public static  boolean isAsseIdRule(String ruleType) {
        return RuleType.AsseIdRule.name().equals(ruleType);
    }

    public static  boolean isUserRule(String ruleType) {
        return RuleType.UserRule.name().equals(ruleType);
    }

    /**
     * 参照 TransactionData.preCheck() 增加规则合法性检查
     */
    public static String preRuleCheck(RuleData rd) {
        // 1. 基础非空校验
        if (rd.from == null || rd.from.isEmpty()) return "Missing Rule Publisher (from)";
        if (rd.ruleJson == null || rd.ruleJson.isEmpty()) return "Rule content (ruleJson) cannot be empty";

        // 2. 严格判断 ruleType 类型
        boolean isValidType = false;
        if (rd.ruleType != null) {
            for (RuleType t : RuleType.values()) {
                if (t.name().equals(rd.ruleType)) {
                    isValidType = true;
                    break;
                }
            }
        }
        if (!isValidType) {
            return "Invalid ruleType: [" + rd.ruleType + "]. Must be one of RuleType enum.";
        }
        // 3. 结构化检查：确保 ruleJson 是合法的 JSON 格式
        try {
            JSON.parseObject(rd.ruleJson);
        } catch (Exception e) {
            return "ruleJson must be a valid JSON string";
        }

        return null; // 校验通过
    }
}
