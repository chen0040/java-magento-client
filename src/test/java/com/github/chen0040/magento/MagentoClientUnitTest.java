package com.github.chen0040.magento;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
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
      logger.info("my account: {}", client.getMyAccount());
      logger.info("product types: {}", client.listProductTypes());
   }

   @Test
   public void test_login_admin(){
      MagentoClient client = new MagentoClient(Mediator.url);
      String token = client.loginAsAdmin(Mediator.adminUsername, Mediator.adminPassword);
      logger.info("account with id = 1: {}", client.getAccountById(1));
      logger.info("product types: \r\n{}", JSON.toJSONString(client.listProductTypes(), SerializerFeature.PrettyFormat));
      logger.info("product page: \r\n{}", JSON.toJSONString(client.listProducts(0, 10), SerializerFeature.PrettyFormat));
   }
}
