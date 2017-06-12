package com.github.chen0040.magento;

import com.alibaba.fastjson.JSON;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by xschen on 3/10/16.
 */
public class HttpClient implements Serializable {
   private static final String DATA_ENCODING = "UTF-8";
   private static final Logger logger = LoggerFactory.getLogger(HttpClient.class);
   private static final long serialVersionUID = 4661645115933875389L;


   private static CloseableHttpClient buildClient() {
      //HttpClientBuilder builder = HttpClientBuilder.create();

      int timeout = 60;
      RequestConfig config = RequestConfig.custom()
              .setSocketTimeout(timeout * 1000)
              .setConnectionRequestTimeout(timeout * 1000)
              .setConnectTimeout(timeout * 1000).build();


      return HttpClients.custom().setDefaultRequestConfig(config).build(); //builder.build();

   }


   public static void formPost(String uri, Map<String, String> parameters) throws IOException {

      logger.info("form post to {}", uri);

      HttpPost httpPost = new HttpPost(uri);
      CloseableHttpClient client = buildClient();
      List<NameValuePair> params = new ArrayList<>();
      for(Map.Entry<String, String> entry : parameters.entrySet()) {
         params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
      }
      httpPost.setEntity(new UrlEncodedFormEntity(params));

      CloseableHttpResponse response = client.execute(httpPost);
      int statusCode = response.getStatusLine().getStatusCode();

      logger.info("status code: {}", statusCode);



   }

   public static String jsonPost(final String url, Map<String, String> parameters) {
      CloseableHttpClient httpClient = buildClient();
      String json = "";
      String body = JSON.toJSONString(parameters);
      try {
         HttpPost request = new HttpPost(url);
         StringEntity params = new StringEntity(body);
         request.addHeader("content-type", "application/json");
         request.setEntity(params);
         CloseableHttpResponse result = httpClient.execute(request);
         if (result.getEntity() != null) {
            json = EntityUtils.toString(result.getEntity(), DATA_ENCODING);
         }
         result.close();
         httpClient.close();
      }
      catch (IOException ex) {
         json = ex.getMessage();
      }

      return json;
   }

   public static String delete(final String url, final Map<String, String> headers) {

      CloseableHttpClient httpClient = buildClient();
      String json = "";
      try {
         HttpDelete request = new HttpDelete(url);

         for (Map.Entry<String, String> entry : headers.entrySet()) {
            request.addHeader(entry.getKey(), entry.getValue());
         }

         CloseableHttpResponse response = httpClient.execute(request);
         if (response.getEntity() != null) {
            json = EntityUtils.toString(response.getEntity(), DATA_ENCODING);
         }
         response.close();
         httpClient.close();
      }
      catch (IOException ex) {
         json = ex.getMessage();
      }
      return json;
   }


   public static String delete(final String url) {

      CloseableHttpClient httpClient = buildClient();
      String json = "";
      try {
         HttpDelete request = new HttpDelete(url);
         request.addHeader("content-type", "application/json");
         CloseableHttpResponse response = httpClient.execute(request);
         if (response.getEntity() != null) {
            json = EntityUtils.toString(response.getEntity(), DATA_ENCODING);
         }
         response.close();
         httpClient.close();
      }
      catch (IOException ex) {
         json = ex.getMessage();
      }
      return json;
   }


   public static String get(final String url) {
      String json = "";
      try {
         CloseableHttpClient httpClient = buildClient();
         HttpGet request = new HttpGet(url);
         request.addHeader("content-type", "application/json");
         CloseableHttpResponse response = httpClient.execute(request);
         if (response.getEntity() != null) {
            json = EntityUtils.toString(response.getEntity(), DATA_ENCODING);
         }
         //logger.info("spark[tryReadAlgorithmModuleStatus]: "+json);
      }
      catch (Exception ex2) {
         json = ex2.getMessage();
      }

      return json;
   }


   public static String get(final String url, final Map<String, String> headers) {
      String json = "";
      try {
         CloseableHttpClient httpClient = buildClient();
         HttpGet request = new HttpGet(url);
         for (Map.Entry<String, String> entry : headers.entrySet()) {
            request.addHeader(entry.getKey(), entry.getValue());
         }
         CloseableHttpResponse response = httpClient.execute(request);
         if (response.getEntity() != null) {
            json = EntityUtils.toString(response.getEntity(), DATA_ENCODING);
         }
         //logger.info("spark[tryReadAlgorithmModuleStatus]: "+json);
      }
      catch (Exception ex2) {
         json = ex2.getMessage();
      }

      return json;
   }
}
