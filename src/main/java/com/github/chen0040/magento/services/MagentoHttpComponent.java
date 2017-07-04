package com.github.chen0040.magento.services;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.TypeReference;
import com.github.chen0040.magento.utils.HttpClient;
import com.github.chen0040.magento.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by xschen on 12/6/2017.
 */
public abstract class MagentoHttpComponent {
   private static final Logger logger = LoggerFactory.getLogger(MagentoHttpComponent.class);

   public abstract String token();
   public abstract String baseUri();

   protected HttpComponent httpComponent;

   public MagentoHttpComponent(HttpComponent httpComponent){
      this.httpComponent = httpComponent;
   }

   public HttpComponent getHttpComponent(){
      return httpComponent;
   }

   public void setHttpComponent(HttpComponent httpComponent){
      this.httpComponent = httpComponent;
   }

   public String postSecure(String url, String body){
      Map<String, String> headers = new HashMap<>();
      if(!StringUtils.isEmpty(this.token())) {
         headers.put("Authorization", "Bearer " + this.token());
      }
      headers.put("Content-Type", "application/json");
      return httpComponent.post(url, body, headers);
   }

   public String putSecure(String url, String body) {
      Map<String, String> headers = new HashMap<>();
      if(!StringUtils.isEmpty(this.token())) {
         headers.put("Authorization", "Bearer " + this.token());
      }
      headers.put("Content-Type", "application/json");
      return httpComponent.put(url, body, headers);
   }

   public String deleteSecure(String url) {
      Map<String, String> headers = new HashMap<>();
      if(!StringUtils.isEmpty(this.token())) {
         headers.put("Authorization", "Bearer " + this.token());
      }
      headers.put("Content-Type", "application/json");
      return httpComponent.delete(url, headers);
   }

   public String getSecured(String uri) {
      Map<String, String> headers = new HashMap<>();
      if(!StringUtils.isEmpty(this.token())) {
         headers.put("Authorization", "Bearer " + this.token());
      }
      headers.put("Content-Type", "application/json");
      return httpComponent.get(uri, headers);
   }

   public String escape(String text) {
      String result = text;
      try{
         result = URLEncoder.encode(text, "UTF-8");
      }
      catch (UnsupportedEncodingException e) {
         logger.error("Failed to escape " + text, e);
      }
      return result;
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
