package qcc.core.qccData;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class QccDataBaseFactory {

    /**
     * 将数据对象序列化为确定性 JSON（OrderedField）
     */
    public static String toJson(QccBaseData data) {
        JSONWriter writer = JSONWriter.of(
                JSONWriter.Feature.WriteMapNullValue,   // 写 null
                JSONWriter.Feature.NotWriteDefaultValue, // 不写默认值
                JSONWriter.Feature.FieldBased     // 关键！强制用字段模式，所有 protected 注解生效
        );
        writer.writeAny(data);
        return writer.toString();
    }

    public static String toJson(List<QccBaseData> list) {

        JSONWriter writer = JSONWriter.of(
                JSONWriter.Feature.WriteMapNullValue,
                JSONWriter.Feature.NotWriteDefaultValue,
                JSONWriter.Feature.FieldBased           // ← 新加！
        );

        writer.startArray();
        for (QccBaseData data : list) {
            writer.writeAny(data); // 这里 fastjson2 会自动序列化子类
        }
        writer.endArray();

        return writer.toString();
    }



    /**
     * JSON → 对象
     */
    public static QccBaseData fromJson(String json) {
        JSONObject obj = JSON.parseObject(json);
        String type = obj.getString("type");
        System.out.println("解析数据对象类型数据内容:"+json);
        if ("transaction".equals(type)) {
            return JSON.parseObject(json, TransactionData.class);
        }
        if ("entity".equals(type)) {
            return JSON.parseObject(json, EntityData.class);
        }
        if ("rule".equals(type)) {
            return JSON.parseObject(json, RuleData.class);
        }

        throw new RuntimeException("Unknown data type: " + type);
    }

    /**
     * 对象 → byte[]（未来主网可替换为二进制格式）
     */
    public static byte[] toBytes(QccBaseData data) {
        return toJson(data).getBytes(StandardCharsets.UTF_8);
    }

    /**
     * byte[] → 对象（JSON 模式）
     */
    public static QccBaseData fromBytes(byte[] bytes) {
        return fromJson(new String(bytes, StandardCharsets.UTF_8));
    }
}

