package com.github.chen0040.magento;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by xschen on 12/6/2017.
 */
public class MagentoClient implements Serializable {
   private static final long serialVersionUID = 3001998767951271632L;
   private static final String relativePath4LoginAsClient = "rest/V1/integration/customer/token";
   private static final String relativePath4LoginAsAdmin = "rest/V1/integration/admin/token";

   private static final String relativePath4Products = "rest/V1/products";

   private static final Logger logger = LoggerFactory.getLogger(MagentoClient.class);

   private String token = null;

   private String baseUri = "";

   public MagentoClient(String baseUri) {
      this.baseUri = baseUri;
   }

   public String listProduct() {
      String uri = baseUri + "/" + relativePath4Products;
      return getSecured(uri);
   }

   private String getSecured(String uri) {
      Map<String, String> headers = new HashMap<>();
      headers.put("Authorization", "Bearer " + this.token);
      headers.put("Content-Type", "application/json");
      return HttpClient.get(uri, headers);
   }

   public String getMyAccount() {
      //"http://magento.ll/index.php/rest/V1/customers/me" -H "Authorization: Bearer asdf3hjklp5iuytre"
      String uri = this.baseUri + "/rest/V1/customers/me";
      return getSecured(uri);
   }

   public String loginAsClient(String username, String password) {
      String uri = baseUri + "/" + relativePath4LoginAsClient;
      Map<String, String> data = new HashMap<>();
      data.put("username", username);
      data.put("password", password);
      this.token = StringUtils.stripQuotation(HttpClient.jsonPost(uri, data));
      logger.info("loginAsClient returns: {}", token);
      return token;
   }

   public String loginAsAdmin(String username, String password) {
      String uri = baseUri + "/" + relativePath4LoginAsAdmin;
      Map<String, String> data = new HashMap<>();
      data.put("username", username);
      data.put("password", password);
      token = StringUtils.stripQuotation(HttpClient.jsonPost(uri, data));
      logger.info("loginAsClient returns: {}", token);
      return token;
   }
}
