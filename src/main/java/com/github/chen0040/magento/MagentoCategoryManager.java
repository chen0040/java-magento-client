package com.github.chen0040.magento;


import com.alibaba.fastjson.JSON;
import com.github.chen0040.magento.models.Category;
import com.github.chen0040.magento.models.CategoryProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * Created by xschen on 12/6/2017.
 */
public class MagentoCategoryManager extends MagentoHttpComponent {
   private MagentoClient client;
   private static final String relativePath4Categories = "rest/V1/categories";
   private static final Logger logger = LoggerFactory.getLogger(MagentoCategoryManager.class);

   public MagentoCategoryManager(MagentoClient client) {
      this.client = client;
   }

   public Category page(int pageIndex, int pageSize) {
      String uri = baseUri() + "/" + relativePath4Categories
              + "?searchCriteria[currentPage]=" + pageIndex
              + "&searchCriteria[pageSize]=" + pageSize;
      String json = getSecured(uri);
      if(!validate(json)){
         return null;
      }

      return JSON.parseObject(json, Category.class);
   }

   public Category getCategoryById(long id) {
      String uri = baseUri() + "/" + relativePath4Categories + "/" + id;
      String json = getSecured(uri);
      if(!validate(json)){
         return null;
      }

      return JSON.parseObject(json, Category.class);
   }

   public List<CategoryProduct> getProductsInCategory(long id) {
      String uri = baseUri() + "/" + relativePath4Categories + "/" + id + "/products";
      String json = getSecured(uri);

      if(!validate(json)) {
         return null;
      }

      return JSON.parseArray(json, CategoryProduct.class);
   }


   @Override public String token() {
      return client.token();
   }


   @Override public String baseUri() {
      return client.baseUri();
   }
}
