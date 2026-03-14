package qcc.rule.assetRule;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;

import java.io.Serializable;

/**
 * 资产共识通知对象
 * 用于从 RuleServer 向 Balance/Hub 服务器发送资产变更通知
 */
public class AssetConsensus implements Serializable {

    private final AssetIssueRule assetRule;
    public AssetConsensus(AssetIssueRule rule) {
        this.assetRule = rule;
    }

    /**
     * 直接使用现成的二进制恢复方式 (与 AssetIssueRule 保持一致)
     */
    public static AssetConsensus fromBytes(byte[] data) {
        // 使用与 AssetIssueRule 相同的解析逻辑
        AssetIssueRule rule = JSON.parseObject(data, AssetIssueRule.class,
                JSONReader.Feature.SupportAutoType,
                JSONReader.Feature.FieldBased);
        return new AssetConsensus(rule);
    }

    /**
     * 获取资产 ID
     */
    public String getAssetId() {
        return assetRule.assetId;
    }

    /**
     * 获取精度（余额计算核心）
     */
    public int getPrecision() {
        return assetRule.precision;
    }

    /**
     * 业务调度逻辑：判断该资产是否允许在当前 Hub 进行铸造
     */
    public boolean canMint() {
        return assetRule.mintable;
    }

    /**
     * 业务调度逻辑：判断该资产是否支持冻结逻辑
     */
    public boolean isSecurityAsset() {
        return assetRule.freezable;
    }

    public AssetIssueRule getRawRule() {
        return assetRule;
    }
}