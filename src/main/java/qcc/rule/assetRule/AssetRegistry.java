

package qcc.rule.assetRule;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import qcc.util.LogObj;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;
import java.util.HashSet;
import java.util.Set;

public class AssetRegistry {
    private static final AssetRegistry INSTANCE = new AssetRegistry();
    public static AssetRegistry getInstance() { return INSTANCE; }

    private final ConcurrentHashMap<String, AssetMeta> assetMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Long> supplyMap = new ConcurrentHashMap<>();
    // 🚀 新增：资产ID -> 被冻结地址集合
    private final ConcurrentHashMap<String, Set<String>> frozenAddresses = new ConcurrentHashMap<>();

    // ====================== 1. 权限核对逻辑 ======================

    /**
     * 判断发起地址是否有权管理该资产
     * @param assetId 资产ID
     * @param signer 发起调用的签名地址
     */
    public boolean isOwner(String assetId, String signer) {
        AssetMeta meta = assetMap.get(assetId);
        if (meta == null || signer == null) return false;
        // 校验 AssetMeta 中记录的 owner 字段
        return signer.equalsIgnoreCase(meta.owner);
    }

    // ====================== 2. 冻结管理 ======================

    /**
     * 执行地址冻结/解冻
     * @param signer 发起地址 (需核对权限)
     * @param assetId 资产ID
     * @param targetAddress 目标地址
     * @param freeze true为冻结，false为解冻
     */
    public boolean setAddressFrozen(String signer, String assetId, String targetAddress, boolean freeze) {
        // 1. 权限校验
        if (!isOwner(assetId, signer)) {
            LogObj.errprintln(">>> [Registry] 越权操作：只有 Owner 能冻结地址");
            return false;
        }

        // 2. 检查资产是否支持冻结 (对应 AssetMeta.freezable)
        AssetMeta meta = assetMap.get(assetId);
        if (meta == null || !meta.freezable) {
            LogObj.errprintln(">>> [Registry] 该资产不支持冻结功能");
            return false;
        }

        // 3. 更新冻结表
        frozenAddresses.computeIfAbsent(assetId, k -> new HashSet<>());
        if (freeze) {
            frozenAddresses.get(assetId).add(targetAddress);
            LogObj.println(">>> [Registry] 地址已冻结: " + targetAddress + " (资产: " + assetId + ")");
        } else {
            frozenAddresses.get(assetId).remove(targetAddress);
            LogObj.println(">>> [Registry] 地址已解冻: " + targetAddress);
        }
        return true;
    }

    /**
     * 判断地址是否被冻结
     */
    public boolean isFrozen(String assetId, String address) {
        Set<String> frozenSet = frozenAddresses.get(assetId);
        return frozenSet != null && frozenSet.contains(address);
    }

    //查询资产是否注册
    public boolean isAssetIdAvailable(String assetId) {
        if (assetId == null || assetId.trim().isEmpty()) return false;
        return !assetMap.containsKey(assetId);
    }

    //增发资产操作
    public void increaseSupply(String signer, String assetId, long amount) {
        if (!isOwner(assetId, signer)) {
            LogObj.errprintln(">>> [Registry] 越权增发: " + signer);
            return;
        }
        supplyMap.compute(assetId, (id, current) -> (current == null) ? amount : current + amount);
    }
    // ====================== 3. 注册逻辑 ======================

    public boolean registerAsset(AssetMeta meta) {
        if (meta == null || meta.assetId == null) return false;
        // 如果资产已存在，不允许重复注册
        if (assetMap.containsKey(meta.assetId)) return false;

        assetMap.put(meta.assetId, meta);
        if (meta.isFinancial() && meta.initialSupply>0) {
            supplyMap.put(meta.assetId,meta.initialSupply);
        }
        return true;
    }

    /**
     * 生成当前状态的快照 JSON
     */
    public String toJson() {
        return JSON.toJSONString(this, JSONWriter.Feature.WriteClassName, JSONWriter.Feature.FieldBased);
    }

    public static AssetRegistry fromJson(String json){
        return JSON.parseObject(json, AssetRegistry.class);
    }
    /**
     * 从快照恢复数据
     */
    // 修改 AssetRegistry.java 中的 loadFromSnapshot
    public static void loadFromSnapshot(String json) {
        if (json == null || json.isEmpty()) return;
        try {
            AssetRegistry snapshot = JSON.parseObject(json, AssetRegistry.class);
            if (snapshot != null) {
                INSTANCE.assetMap.clear();
                INSTANCE.supplyMap.clear();
                INSTANCE.frozenAddresses.clear();

                INSTANCE.assetMap.putAll(snapshot.assetMap);
                INSTANCE.supplyMap.putAll(snapshot.supplyMap);
                INSTANCE.frozenAddresses.putAll(snapshot.frozenAddresses);
            }
        } catch (Exception e) {
            LogObj.errprintln(">>> [Registry] 加载快照失败");
        }
    }
    /**
     * [新增] 持久化快照到磁盘
     * @param filePath 保存路径 (如: "./data/asset_registry.json")
     */
    public void saveSnapshot(String filePath) {
        try {
            String json = JSON.toJSONString(this, JSONWriter.Feature.FieldBased);
            Files.writeString(Path.of(filePath), json);
            LogObj.println(">>> [Registry] 快照保存成功");
        } catch (Exception e) {
            LogObj.errprintln(">>> [Registry] 快照保存异常: " + e.getMessage());
        }
    }
}