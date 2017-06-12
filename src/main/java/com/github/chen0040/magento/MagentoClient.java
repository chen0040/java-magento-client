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

   public String listProduct(String uri) {
      uri = uri + "/" + relativePath4Products;
      Map<String, String> headers = new HashMap<>();
      headers.put("Authorization", "Bearer " + this.token);
      return HttpClient.get(uri, headers);
   }

   public String loginAsClient(String uri, String username, String password) {
      uri = uri + "/" + relativePath4LoginAsClient;
      Map<String, String> data = new HashMap<>();
      data.put("username", username);
      data.put("password", password);
      this.token = HttpClient.jsonPost(uri, data);
      logger.info("loginAsClient returns: {}", token);
      return token;
   }

   public String loginAsAdmin(String uri, String username, String password) {
      uri = uri + "/" + relativePath4LoginAsAdmin;
      Map<String, String> data = new HashMap<>();
      data.put("username", username);
      data.put("password", password);
      token = HttpClient.jsonPost(uri, data);
      logger.info("loginAsClient returns: {}", token);
      return token;
   }
}
