package qcc.rule;


/**
 * 规则执行接口枚举：强制发布与执行一一对应
 */
public enum RuleApiType {
    // --- 创世规则 (GodRule) ---
    GENESIS_INIT(RuleType.GodRule, 0, "GenesisRule"),

    // --- 系统规则 (SystemRule) ---
    GAS_POLICY_UPDATE(RuleType.SystemRule, 100, "GasRuleObj"),
    GOVERNANCE_ADDR_CHANGE(RuleType.SystemRule, 200, "GovernanceRule"),

    // --- 资产规则 (AssetIdRule) ---
    ASSET_ISSUE(RuleType.AsseIdRule, 50, "AssetIssueRule"),
    ASSET_MINT(RuleType.AsseIdRule, 20, "AssetMintRule"),
    ASSET_FREEZE_ADDR(RuleType.AsseIdRule, 10, "AssetFreezeRule"),

    // --- 用户规则 (UserRule) ---
    CALLBACK_REG(RuleType.UserRule, 5, "CallbackRule"),
    ADDR_SUBSCRIBE(RuleType.UserRule, 2, "SubscribeRule");

    public final RuleType domain;
    public final int rFactor;
    public final String handlerClass; // 对应的 Java 逻辑类名

    RuleApiType(RuleType domain, int rFactor, String handlerClass) {
        this.domain = domain;
        this.rFactor = rFactor;
        this.handlerClass = handlerClass;
    }
}