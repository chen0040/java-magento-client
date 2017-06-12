package com.github.chen0040.magento;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;


/**
 * Created by xschen on 12/6/2017.
 */
public class MagentoClientUnitTest {

   private static final Logger logger = LoggerFactory.getLogger(MagentoClientUnitTest.class);
   @Test
   public void test_login_client(){
      MagentoClient client = new MagentoClient();
      String token = client.loginAsClient(Mediator.url, Mediator.customerUsername, Mediator.customerPassword);

   }

   @Test
   public void test_login_admin(){
      MagentoClient client = new MagentoClient();
      String token = client.loginAsAdmin(Mediator.url, Mediator.adminUsername, Mediator.adminPassword);
      String json = client.listProduct(Mediator.url);
      logger.info(json);
   }
}
