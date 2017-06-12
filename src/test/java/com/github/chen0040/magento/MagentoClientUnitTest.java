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
      MagentoClient client = new MagentoClient(Mediator.url);
      String token = client.loginAsClient(Mediator.customerUsername, Mediator.customerPassword);
      logger.info(client.getMyAccount());
   }

   @Test
   public void test_login_admin(){
      MagentoClient client = new MagentoClient(Mediator.url);
      String token = client.loginAsAdmin(Mediator.adminUsername, Mediator.adminPassword);
      String json = client.listProduct();
      logger.info(json);
   }
}
