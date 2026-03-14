package qcc.rule.qccRule;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import qcc.rule.RuleBaseObj;
import qcc.rule.RuleContinuousType;
import qcc.rule.RuleType;
import qcc.util.LogObj;

import java.io.File;
import java.nio.file.Files;

/**
 * Gas 全球治理规则对象 (QCC 创世版)
 * 修正逻辑：
 * 1. QCC 原生币转账：固定标定 0.001 (乘 G因子)
 * 2. 代币/资产转账：固定标定 0.15 (乘 G因子)
 * 3. 实体数据：按 KB 计费
 */
public class GasRuleObj extends RuleBaseObj {

    public double gFactor = 1.0;            // G-因子：动态汇率调节系数
    public String governanceMemo;           // 治理说明

    // --- 标定基准 (以“个”为单位) ---
    public double nativeTxBaseGas = 0.001;  // QCC 原生币固定标定
    public double tokenTxBaseGas = 0.15;    // 代币转账固定标定
    public long minTxBasegase=1000000000L;  // 最低tx 交易持有gas费率

    // --- 实体数据 (DATA) 参数 ---
    public double dataPricePerKb = 0.001;
    public long dataMaxSize = 10 * 1024 * 1024; // 10MB限制

    // --- 规则交易 (RULE) 参数 ---
    public double ruleBaseGas = 100.0;

    // --- 新增系统级字段 ---
    private static GasRuleObj instance;
   // public String gasReceiverAddress = "Q1CmbMJQBEt1ugUeGUa3NT8HtmcGgpD9zmE"; // 测试用收款地址

    public GasRuleObj() {
        this.ruleType = RuleType.SystemRule;
        this.ruleContinuousType = RuleContinuousType.Replace;
        this.governance = true;
        this.handlerClass = this.getClass().getSimpleName();
        this.ruleName = "GasRule";
        this.version = "1.0.0";
        this.governanceMemo = "Genesis Gas Policy: Native=0.001, Token=0.15, G=1.0";
    }

    /**
     * 系统单例获取
     */
    public static synchronized GasRuleObj getInstance() {
        if (instance == null) instance = new GasRuleObj();
        return instance;
    }

    /**
     * 根据资产类型路由计算 Gas
     * @param assetId 资产符号 (如 QCC, USDT)
     * @param amount 原子单位金额 (10^18)
     */
    public double calculateGas(String assetId, long amount) {
        if ("QCC".equalsIgnoreCase(assetId)) {
            return nativeTxBaseGas * gFactor;
        } else {
            // 代币转账：直接 0.15 标定
            return tokenTxBaseGas * gFactor;
        }
    }

    /**
     * 计算数据容器 (DATA) 的 Gas
     */
    public double calculateDataGas(long byteSize) {
        if (byteSize > dataMaxSize) return -1;
        double kb = byteSize / 1024.0;
        return (kb * dataPricePerKb) * gFactor;
    }

    /**
     * 治理接口：更新 G-因子
     */
    public void updateGFactor(double newG, String reason) {
        this.gFactor = newG;
        this.governanceMemo = reason;
    }

    public byte[] toBytes() {
        return JSON.toJSONBytes(this, JSONWriter.Feature.WriteClassName, JSONWriter.Feature.FieldBased);
    }

    public static GasRuleObj fromBytes(byte[] data) {
        return JSON.parseObject(data, GasRuleObj.class, JSONReader.Feature.SupportAutoType, JSONReader.Feature.FieldBased);
    }

    /**
     * 快照恢复逻辑
     */
    public void loadSnapshot(String path) {
        try {
            File f = new File(path);
            if (f.exists()) {
                fromBytes(Files.readAllBytes(f.toPath()));
                LogObj.println("💾 [GasRule] 已从本地快照恢复规则状态");
            }
        } catch (Exception e) {
            LogObj.errprintln("无法恢复Gas快照: " + e.getMessage());
        }
    }

    public void saveSnapshot(String path) {
        try {
            Files.write(new File(path).toPath(), toBytes());
        } catch (Exception e) {
            LogObj.errprintln("快照保存失败: " + e.getMessage());
        }
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