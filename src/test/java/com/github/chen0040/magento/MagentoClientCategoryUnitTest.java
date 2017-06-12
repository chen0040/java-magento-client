package com.github.chen0040.magento;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.chen0040.magento.models.Category;
import com.github.chen0040.magento.models.CategoryProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.List;


/**
 * Created by xschen on 12/6/2017.
 */
public class MagentoClientCategoryUnitTest {

   private static final Logger logger = LoggerFactory.getLogger(MagentoClientCategoryUnitTest.class);

   @Test
   public void test_get_category_by_id(){
      long id = 15;

      MagentoClient client = new MagentoClient(Mediator.url);
      client.loginAsAdmin(Mediator.adminUsername, Mediator.adminPassword);
      Category category = client.categories().getCategoryById(id);
      logger.info("category:\r\n{}", JSON.toJSONString(category, SerializerFeature.PrettyFormat));
   }

   @Test
   public void test_list_categories() {
      MagentoClient client = new MagentoClient(Mediator.url);
      client.loginAsAdmin(Mediator.adminUsername, Mediator.adminPassword);

      Category page = client.categories().page(0, 10);
      logger.info("categories: {}\r\n", JSON.toJSONString(page, SerializerFeature.PrettyFormat));
   }

   @Test
   public void test_list_products_in_category() {
      long id = 15;
      MagentoClient client = new MagentoClient(Mediator.url);
      client.loginAsAdmin(Mediator.adminUsername, Mediator.adminPassword);

      List<CategoryProduct> products = client.categories().getProductsInCategory(id);
      logger.info("products in category 15:\r\n{}", JSON.toJSONString(products, SerializerFeature.PrettyFormat));
   }
}