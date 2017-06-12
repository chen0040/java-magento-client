package com.github.chen0040.magento;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.chen0040.magento.models.Account;
import com.github.chen0040.magento.models.Product;
import com.github.chen0040.magento.models.ProductPage;
import com.github.chen0040.magento.models.MagentoType;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by xschen on 12/6/2017.
 */
@Getter
@Setter
public class MagentoClient implements Serializable {
   private static final long serialVersionUID = 3001998767951271632L;
   private static final String relativePath4LoginAsClient = "rest/V1/integration/customer/token";
   private static final String relativePath4LoginAsAdmin = "rest/V1/integration/admin/token";

   private static final String relativePath4Products = "rest/V1/products";

   private static final Logger logger = LoggerFactory.getLogger(MagentoClient.class);

   private String token = null;

   private String baseUri = "";

   private boolean admin = false;

   private boolean authenticated = false;

   public MagentoClient(String baseUri) {
      this.baseUri = baseUri;
   }

   public ProductPage listProducts(int pageIndex, int pageSize) {
      String uri = baseUri + "/" + relativePath4Products
              + "?searchCriteria[currentPage]=" + pageIndex
              + "&searchCriteria[pageSize]=" + pageSize;
      String json = getSecured(uri);
      if(!validate(json)){
         return null;
      }

      return JSON.parseObject(json, ProductPage.class);
   }

   public Product getProductBySku(String sku) {
      String uri = baseUri + "/" + relativePath4Products + "/" + sku;
      String json = getSecured(uri);

      if(!validate(json)){
         return null;
      }

      return JSON.parseObject(json, Product.class);
   }

   public boolean hasProduct(String sku) {
      return getProductBySku(sku) != null;
   }

   public Product addProduct(Product product){
      String sku = product.getSku();
      String url = baseUri + "/" + relativePath4Products + "/" + sku;

      Map<String, Object> detail = new HashMap<>();

      detail.put("sku", product.getSku());
      detail.put("name", product.getName());
      detail.put("price", product.getPrice());
      detail.put("status", product.getStatus());
      detail.put("type_id", product.getType_id());
      detail.put("attribute_set_id", product.getAttribute_set_id());
      detail.put("weight", product.getWeight());

      Map<String, Object> req = new HashMap<>();
      req.put("product", detail);

      String body = JSON.toJSONString(req, SerializerFeature.PrettyFormat);
      logger.info("posting:\r\n{}", body);
      String json = putSecure(url, body);

      if(!validate(json)){
         return null;
      }

      return JSON.parseObject(json, Product.class);
   }

   public String postSecure(String url, String body){
      Map<String, String> headers = new HashMap<>();
      headers.put("Authorization", "Bearer " + this.token);
      headers.put("Content-Type", "application/json");
      return HttpClient.post(url, body, headers);
   }

   private String putSecure(String url, String body) {
      Map<String, String> headers = new HashMap<>();
      headers.put("Authorization", "Bearer " + this.token);
      headers.put("Content-Type", "application/json");
      return HttpClient.put(url, body, headers);
   }

   private String deleteSecure(String url) {
      Map<String, String> headers = new HashMap<>();
      headers.put("Authorization", "Bearer " + this.token);
      headers.put("Content-Type", "application/json");
      return HttpClient.delete(url, headers);
   }

   public String listProducts(String name, String value, String condition_type) {
      String uri = baseUri + "/" + relativePath4Products
              + "?searchCriteria[filter_groups][0][filters][0][field]=category_gear"
              + "&searchCriteria[filter_groups][0][filters][0][value]=86"
              + "&searchCriteria[filter_groups][0][filters][0][condition_type]=finset";
      return getSecured(uri);
   }

   public List<MagentoType> listProductTypes() {
      String uri = baseUri + "/rest/V1/products/types"
              + "?searchCriteria[currentPage]=0"
              + "&searchCriteria[pageSize]=1000";
      String json = getSecured(uri);
      return JSON.parseArray(json, MagentoType.class);
   }

   public List<MagentoType> listProductTypes(int page, int pageSize) {
      String uri = baseUri + "/rest/V1/products/types"
              + "?searchCriteria[currentPage]=" + page
              + "&searchCriteria[pageSize]=" + pageSize;
      String json = getSecured(uri);
      return JSON.parseArray(json, MagentoType.class);
   }

   public String getSecured(String uri) {
      Map<String, String> headers = new HashMap<>();
      headers.put("Authorization", "Bearer " + this.token);
      headers.put("Content-Type", "application/json");
      return HttpClient.get(uri, headers);
   }


   public Account getMyAccount() {
      if(admin){
         logger.warn("my account access api is not supported for admin rest call");
         return null;
      }

      //"http://magento.ll/index.php/rest/V1/customers/me" -H "Authorization: Bearer asdf3hjklp5iuytre"
      String uri = this.baseUri + "/rest/V1/customers/me";
      String json = getSecured(uri);

      if(!validate(json)) {
         return null;
      }

      return JSON.parseObject(json, Account.class);
   }

   private boolean validate(String json) {
      Map<String, Object> data = JSON.parseObject(json, new TypeReference<Map<String, Object>>(){}.getType());

      if(data.containsKey("message")) {
         logger.error("query failed: {}", data.get("message"));
         logger.warn("trace: {}", data.get("trace"));
         return false;
      }
      return true;
   }

   public Map<String, Object> getAccountById(long id) {
      if(!admin){
         logger.warn("other account access api is not supported for client rest call");
         return new HashMap<>();
      }

      String uri = this.baseUri + "/rest/V1/customers/" + id;
      String json = getSecured(uri);
      Map<String, Object> data = JSON.parseObject(json, new TypeReference<Map<String, Object>>(){}.getType());
      return data;
   }

   public String loginAsClient(String username, String password) {
      String uri = baseUri + "/" + relativePath4LoginAsClient;
      Map<String, String> data = new HashMap<>();
      data.put("username", username);
      data.put("password", password);
      this.token = StringUtils.stripQuotation(HttpClient.jsonPost(uri, data));
      logger.info("loginAsClient returns: {}", token);
      if(!token.contains("Invalid login or password")){
         authenticated = true;
      }
      return token;
   }

   public String loginAsAdmin(String username, String password) {
      String uri = baseUri + "/" + relativePath4LoginAsAdmin;
      Map<String, String> data = new HashMap<>();
      data.put("username", username);
      data.put("password", password);
      token = StringUtils.stripQuotation(HttpClient.jsonPost(uri, data));
      logger.info("loginAsClient returns: {}", token);
      if(!token.contains("Invalid login or password")){
         authenticated = true;
      }
      return token;
   }


   public String deleteProduct(String sku) {
      String url = baseUri + "/" + relativePath4Products + "/" + sku;
      return deleteSecure(url);
   }
}
