package qcc.rule.qccRule;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import qcc.rule.RuleBaseObj;
import qcc.rule.RuleContinuousType;
import qcc.rule.RuleType;

import java.util.HashSet;
import java.util.Set;

/**
 * 规则治理地址对象：定义 QCC 规则更新的准入权限
 * 对应 RuleType.SystemRule 的权限校验
 */
public class RuleAddress extends RuleBaseObj {

    // 管理员地址池：只有池内的地址才能发布 SystemRule
    private Set<String> adminAddresses = new HashSet<>();
    // 治理说明
    public String governanceMemo;
    private static RuleAddress instance;

    public RuleAddress() {
        this.ruleType = RuleType.SystemRule; // 固定为系统级规则
        this.ruleContinuousType = RuleContinuousType.Replace;
        this.governance = true;
        this.handlerClass = this.getClass().getSimpleName();
        this.ruleName = "RuleAddressManager";
        this.version = "1.0.0";
       // this.governanceMemo = "Genesis Admin Address Management";
        // 默认将创世主地址加入管理权限
        //adminAddresses.add("Q1CmbMJQBEt1ugUeGUa3NT8HtmcGgpD9zmE");
    }

    /**
     * 系统单例获取，保持与 GasRuleObj 一致
     */
    public static synchronized RuleAddress getInstance() {
        if (instance == null) instance = new RuleAddress();
        return instance;
    }

    /**
     * 核心校验逻辑：判断一个地址是否有权更新系统规则
     */
    public boolean isAdmin(String address) {
        return adminAddresses.contains(address);
    }

    /**
     * 治理接口：新增管理员
     */
    public void addAdmin(String newAdmin, String reason) {
        this.adminAddresses.add(newAdmin);
        this.governanceMemo = "Admin Added: " + reason;
    }

    /**
     * 治理接口：移除管理员
     */
    public void removeAdmin(String oldAdmin, String reason) {
        this.adminAddresses.remove(oldAdmin);
        this.governanceMemo = "Admin Removed: " + reason;
    }

    public Set<String> getAdminAddresses() {
        return adminAddresses;
    }

    // ====================== 序列化支持 (与 GasRuleObj 对齐) ======================

    public byte[] toBytes() {
        return JSON.toJSONBytes(this, JSONWriter.Feature.WriteClassName, JSONWriter.Feature.FieldBased);
    }

    public static RuleAddress fromBytes(byte[] data) {
        return JSON.parseObject(data, RuleAddress.class, JSONReader.Feature.SupportAutoType, JSONReader.Feature.FieldBased);
    }

    /**
     * 将规则对象转换为格式化的 JSON 字符串
     * 用于日志打印及索引同步
     */
    public String toJson() {
        // 使用 PrettyFormat 增加可读性
        return JSON.toJSONString(this, JSONWriter.Feature.PrettyFormat);
    }
}