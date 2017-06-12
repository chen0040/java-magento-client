package com.github.chen0040.magento;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.chen0040.magento.models.ProductPage;
import com.github.chen0040.magento.models.ProductType;
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
      return JSON.parseObject(json, ProductPage.class);
   }

   public String listProducts(String name, String value, String condition_type) {
      String uri = baseUri + "/" + relativePath4Products
              + "?searchCriteria[filter_groups][0][filters][0][field]=category_gear"
              + "&searchCriteria[filter_groups][0][filters][0][value]=86"
              + "&searchCriteria[filter_groups][0][filters][0][condition_type]=finset";
      return getSecured(uri);
   }

   public List<ProductType> listProductTypes() {
      String uri = baseUri + "/rest/V1/products/types"
              + "?searchCriteria[filter_groups][0][filters][0][field]=category_gear"
              + "&searchCriteria[filter_groups][0][filters][0][value]=86"
              + "&searchCriteria[filter_groups][0][filters][0][condition_type]=finset";
      String json = getSecured(uri);
      return JSON.parseArray(json, ProductType.class);
   }

   public String getSecured(String uri) {
      Map<String, String> headers = new HashMap<>();
      headers.put("Authorization", "Bearer " + this.token);
      headers.put("Content-Type", "application/json");
      return HttpClient.get(uri, headers);
   }


   public Map<String, Object> getMyAccount() {
      if(admin){
         logger.warn("my account access api is not supported for admin rest call");
         return new HashMap<>();
      }

      //"http://magento.ll/index.php/rest/V1/customers/me" -H "Authorization: Bearer asdf3hjklp5iuytre"
      String uri = this.baseUri + "/rest/V1/customers/me";
      String json = getSecured(uri);
      Map<String, Object> data = JSON.parseObject(json, new TypeReference<Map<String, Object>>(){}.getType());
      return data;
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
}
