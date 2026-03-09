package qcc.core.qccData;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.annotation.JSONField;
import com.alibaba.fastjson2.annotation.JSONType;

@JSONType(typeName = "rule")
public class RuleData extends QueBaseData {

    private static final String TYPE = "rule";

    @JSONField(ordinal = 7)
    public String ruleType;

    @JSONField(ordinal = 8)
    public String ruleJson;

    public RuleData(String assetId) {
        initBaseFields(assetId);
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String getPayloadForHash() {
        return id + "|" + assetId + "|" + ruleType + "|" + ruleJson;
    }

    @Override
    public byte[] toBytes() {
        return new byte[0];
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }
}
