package qcc.rule.userRule;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import qcc.core.qccData.RuleData;
import qcc.util.LogObj;
import java.io.File;
import java.nio.file.Files;
import java.util.concurrent.ConcurrentHashMap;

public class UserRuleManager {
    private static UserRuleManager instance;

    // 分类管理集合
    public ConcurrentHashMap<String, PayCallRule> payCallRules = new ConcurrentHashMap<>();
    public ConcurrentHashMap<String, MerchantRule> merchantRules = new ConcurrentHashMap<>();
    public ConcurrentHashMap<String, DeveloperRule> developerRules = new ConcurrentHashMap<>();
    public ConcurrentHashMap<String, MultiSignature> multiSigRules = new ConcurrentHashMap<>();

    public static synchronized UserRuleManager getInstance() {
        if (instance == null) instance = new UserRuleManager();
        return instance;
    }

    /**
     * 快照保存：将所有集合序列化为二进制
     */
    public void saveSnapshot(String path) {
        try {
            byte[] data = JSON.toJSONBytes(this, JSONWriter.Feature.FieldBased);
            Files.write(new File(path).toPath(), data);
            LogObj.println("💾 [UserManager] 用户规则快照已保存");
        } catch (Exception e) {
            LogObj.errprintln("快照保存失败: " + e.getMessage());
        }
    }

    /**
     * 快照恢复
     */
    public void loadSnapshot(String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                UserRuleManager restored = JSON.parseObject(Files.readAllBytes(file.toPath()), UserRuleManager.class);
                this.payCallRules = restored.payCallRules;
                this.merchantRules = restored.merchantRules;
                this.developerRules = restored.developerRules;
                this.multiSigRules = restored.multiSigRules;
                LogObj.println("📂 [UserManager] 已恢复用户规则集合");
            }
        } catch (Exception e) {
            LogObj.errprintln("快照恢复失败: " + e.getMessage());
        }
    }

    /**
     * 🚀 判定该地址是否为开启了“Gas代付”的商户
     * @param address 交易的接收者地址
     * @return true: 由该地址支付 Gas; false: 由发起者支付
     */
    public boolean isGasDelegated(String address) {
        if (address == null) return false;
        MerchantRule rule = merchantRules.get(address);
        // 只有规则存在且 gasDelegation 开关为 true 时才代付
        return rule != null && rule.gasDelegation;
    }


    /**
     * 🚀 判定该地址是否为合法的开发者（已申请开发者规则）
     * @param devAddress 交易中携带的 fromdev 地址
     * @return true: 合法开发者，有权获得 Gas 分润
     */
    public boolean isValidDeveloper(String devAddress) {
        if (devAddress == null || devAddress.isEmpty()) return false;
        // 检查开发者规则集合中是否存在该地址
        return developerRules.containsKey(devAddress);
    }


    /**
     * 🚀 统一规则分发器：解析 ruleJson 并根据类型“启动”规则
     * @param rd 链上的原始规则数据
     */
    /**
     * 🚀 统一规则分发器：解析 ruleJson，绑定来源凭证，并激活规则
     * @param rd 链上的原始规则数据
     */
    public void processRule(RuleData rd) {
        if (rd == null || rd.ruleJson == null || rd.ruleName == null) return;

        try {
            String name = rd.ruleName;
            // 获取当前规则交易的唯一哈希，作为该规则的生效凭证
            String txHash = rd.getTxHash();
            // 根据规则名称分发解析逻辑
            if ("PayCallRule".equals(name)) {
                PayCallRule rule = JSON.parseObject(rd.ruleJson, PayCallRule.class);
                rule.credentialHash = txHash; // 🔗 核心修复：绑定规则来源交易哈希
                rule.address = rd.from;       // 🔗 强制绑定：确保规则归属于发起地址
                payCallRules.put(rd.from, rule);

            } else if ("MerchantRule".equals(name)) {
                MerchantRule rule = JSON.parseObject(rd.ruleJson, MerchantRule.class);
                rule.credentialHash = txHash;
                rule.address = rd.from;
                merchantRules.put(rd.from, rule);
            } else if ("DeveloperRule".equals(name)) {
                DeveloperRule rule = JSON.parseObject(rd.ruleJson, DeveloperRule.class);
                rule.credentialHash = txHash;
                rule.address = rd.from;
                developerRules.put(rd.from, rule);
            }
            LogObj.println("✅ [UserRuleManager] 规则已激活并绑定凭证: " + name + " -> " + rd.from + " | Credential: " + txHash);

        } catch (Exception e) {
            LogObj.errprintln("❌ [UserRuleManager] 规则解析或绑定失败: " + rd.ruleName + " | Error: " + e.getMessage());
        }
    }
}
