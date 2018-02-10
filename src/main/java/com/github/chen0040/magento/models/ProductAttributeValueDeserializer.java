package com.github.chen0040.magento.models;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProductAttributeValueDeserializer implements ObjectDeserializer {
    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object o) {
        Object obj = parser.parse();

        List<MagentoAttribute> result = new ArrayList<>();

        if(obj instanceof JSONArray) {
            JSONArray ja = (JSONArray)obj;
            for(int i=0; i < ja.size(); ++i) {
                Object jaObj = ja.get(i);
                if(jaObj instanceof JSONObject){
                    JSONObject jasObj = (JSONObject)jaObj;

                    String key = null;
                    String value = null;
                    if(jasObj.containsKey("attribute_code")){
                        key = jasObj.get("attribute_code").toString();
                    }
                    if(jasObj.containsKey("value")) {
                        value = jasObj.get("value").toString();
                    }
                    if(key != null && value != null) {
                        MagentoAttribute ma = new MagentoAttribute();
                        ma.setAttribute_code(key);
                        ma.setValue(value);
                        result.add(ma);
                    }
                }
            }
        } else if(obj instanceof JSONObject) {
            JSONObject jo = (JSONObject)obj;
            for(String key : jo.keySet()) {
                MagentoAttribute ma = new MagentoAttribute();
                ma.setAttribute_code(key);
                Object joObj = jo.get(key);
                if(joObj instanceof JSONObject) {
                    ma.setValue(((JSONObject)joObj).toJSONString());
                } else if(joObj instanceof JSONArray) {
                    ma.setValue(((JSONArray)joObj).toJSONString());
                } else {
                    ma.setValue(joObj.toString());
                }
                result.add(ma);
            }
        }



        return (T)result;
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }

    /*
    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType,
                      int features) throws IOException {
        Integer value = (Integer) object;
        String text = value + "元";
        serializer.write(text);
    }*/


}