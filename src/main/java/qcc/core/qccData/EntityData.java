package qcc.core.qccData;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.annotation.JSONField;
import com.alibaba.fastjson2.annotation.JSONType;

@JSONType(typeName = "entity")   // 加上这行，保险起见
public class EntityData extends QueBaseData {

    private static final String TYPE = "entity";

    @JSONField(ordinal = 7)
    public String owner;

    @JSONField(ordinal = 8)
    public String content;

    public EntityData(String assetId) {
        initBaseFields(assetId);
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String getPayloadForHash() {
        return id + "|" + assetId + "|" + owner + "|" + content;
    }

    @Override
    public byte[] toBytes() {
        return new byte[0];
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }
}
