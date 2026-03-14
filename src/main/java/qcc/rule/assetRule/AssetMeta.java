package qcc.rule.assetRule;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;

/**
 * 统一资产元数据模型
 * 支持金融资产 (COIN) 与 实体数据资产 (DATA) 的序列化与反序列化
 */
public class AssetMeta {
    public String assetId;
    public String type;        // "COIN" 或 "DATA"
    public String owner;       // 资产所属者/发行人地址
    // --- 金融资产特有 ---
    public int precision;
    public long initialSupply;
    public boolean mintable;
    public boolean freezable;
    // --- 实体数据资产特有 ---
    public String dataContentHash; // 实体数据的哈希指纹
    public String storageLink;     // 外部存储链接 (如 IPFS)
    public String credentialHash;
    public AssetMeta(String assetId,String address){
        this.assetId=assetId;
        this.owner=address;
    }
    public boolean isFinancial() {
        return "COIN".equalsIgnoreCase(type);
    }
    // ====================== 序列化接口 (对接 RuleData) ======================
    /**
     * 转为 JSON 字符串，存入 RuleData.ruleJson
     */
    public String toJson() {
        return JSON.toJSONString(this);
    }

    /**
     * 转为二进制字节，用于网络传输或存储
     */
    public byte[] toBytes() {
        // 使用 FieldBased 提升性能，WriteClassName 确保多态性（如果未来有子类）
        return JSON.toJSONBytes(this, JSONWriter.Feature.FieldBased);
    }

    /**
     * 从 JSON 字符串恢复
     */
    public static AssetMeta fromJson(String json) {
        if (json == null || json.isEmpty()) return null;
        return JSON.parseObject(json, AssetMeta.class, JSONReader.Feature.FieldBased);
    }

    /**
     * 从二进制字节恢复
     */
    public static AssetMeta fromBytes(byte[] data) {
        if (data == null || data.length == 0) return null;
        return JSON.parseObject(data, AssetMeta.class, JSONReader.Feature.FieldBased);
    }
}