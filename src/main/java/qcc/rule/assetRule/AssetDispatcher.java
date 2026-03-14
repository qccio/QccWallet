package qcc.rule.assetRule;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 资产调度管理器 (唯一实例类)
 * 职责：负责全局资产合约的登记、状态维护及调度决策
 */
public class AssetDispatcher {

    // 使用 ConcurrentHashMap 存储已登记的资产 (AssetId -> 共识对象)
    private final ConcurrentHashMap<String, AssetConsensus> activeAssets = new ConcurrentHashMap<>();

    // 1. 私有化构造函数，禁止外部 new
    private AssetDispatcher() {
    }

    // 2. 静态内部类实现单例 (线程安全且延迟加载)
    private static class Holder {
        private static final AssetDispatcher INSTANCE = new AssetDispatcher();
    }

    // 3. 获取唯一实例的静态方法
    public static AssetDispatcher getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 执行资产登记 (对应合约管理逻辑)
     * @param ruleBytes 资产共识规则字节数组
     */
    public void registerAsset(byte[] ruleBytes) {
        AssetConsensus consensus = AssetConsensus.fromBytes(ruleBytes);
        if (consensus.getAssetId() != null) {
            // 实现资产合约登记逻辑
            activeAssets.put(consensus.getAssetId(), consensus);
            //System.out.println(">>> [AssetManager] 资产合约登记成功 ID: " + consensus.getAssetId());
        }
    }

    /**
     * 接收来自 0 号容器或共识更新的通知 (保持原接口兼容)
     */
    public void onAssetNotified(byte[] ruleBytes) {
        registerAsset(ruleBytes);
    }

    /**
     * 校验资产是否已登记且有效
     */
    public boolean isAssetRegistered(String assetId) {
        return activeAssets.containsKey(assetId);
    }

    /**
     * 获取资产精度 (用于 Balance 计算)
     */
    public int getAssetPrecision(String assetId) {
        AssetConsensus c = activeAssets.get(assetId);
        return (c != null) ? c.getPrecision() : 0;
    }

    /**
     * 获取已登记的所有资产 ID (只读集合)
     */
    public Set<String> getManagedAssetIds() {
        return Collections.unmodifiableSet(activeAssets.keySet());
    }

    /**
     * 移除/注销资产合约
     */
    public void unregisterAsset(String assetId) {
        if (activeAssets.remove(assetId) != null) {
            System.out.println(">>> [AssetManager] 资产合约已注销: " + assetId);
        }
    }
}