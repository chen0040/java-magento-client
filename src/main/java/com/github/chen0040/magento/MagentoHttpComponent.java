package com.github.chen0040.magento;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by xschen on 12/6/2017.
 */
public abstract class MagentoHttpComponent {
   private static final Logger logger = LoggerFactory.getLogger(MagentoHttpComponent.class);

   public abstract String token();
   public abstract String baseUri();

   public String postSecure(String url, String body){
      Map<String, String> headers = new HashMap<>();
      headers.put("Authorization", "Bearer " + this.token());
      headers.put("Content-Type", "application/json");
      return HttpClient.post(url, body, headers);
   }

   public String putSecure(String url, String body) {
      Map<String, String> headers = new HashMap<>();
      headers.put("Authorization", "Bearer " + this.token());
      headers.put("Content-Type", "application/json");
      return HttpClient.put(url, body, headers);
   }

   public String deleteSecure(String url) {
      Map<String, String> headers = new HashMap<>();
      headers.put("Authorization", "Bearer " + this.token());
      headers.put("Content-Type", "application/json");
      return HttpClient.delete(url, headers);
   }

   public String getSecured(String uri) {
      Map<String, String> headers = new HashMap<>();
      headers.put("Authorization", "Bearer " + this.token());
      headers.put("Content-Type", "application/json");
      return HttpClient.get(uri, headers);
   }

   protected boolean validate(String json) {
      try {
         Map<String, Object> data = JSON.parseObject(json, new TypeReference<Map<String, Object>>() {
         }.getType());

         if (data.containsKey("message")) {
            logger.error("query failed: {}", data.get("message"));
            logger.warn("trace: {}", data.get("trace"));
            return false;
         }
      } catch(JSONException exception){
         return true;
      }
      return true;
   }
}
