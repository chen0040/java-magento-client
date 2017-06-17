package com.github.chen0040.magento.services;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.chen0040.magento.MagentoClient;
import com.github.chen0040.magento.models.Category;
import com.github.chen0040.magento.models.CategoryProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

   public boolean deleteCaegoryById(long categoryId) {
      String url = baseUri() + "/" + relativePath4Categories + "/" + categoryId;
      String json = deleteSecure(url);
      if(!validate(json)){
         return false;
      }
      return json.equalsIgnoreCase("true");
   }

   public Category all() {
      int pageIndex = 0;
      int pageSize = 1000;
      String uri = baseUri() + "/" + relativePath4Categories
              + "?searchCriteria[currentPage]=" + pageIndex
              + "&searchCriteria[pageSize]=" + pageSize;
      String json = getSecured(uri);
      if(!validate(json)){
         return null;
      }

      return JSON.parseObject(json, Category.class);
   }

   public Category getCategoryByIdClean(long id) {
      String uri = baseUri() + "/" + relativePath4Categories + "/" + id;
      String json = getSecured(uri);
      if(!validate(json)){
         return null;
      }

      return JSON.parseObject(json, Category.class);
   }

   public Category getCategoryByIdWithChildren(long id) {
      Category all = all();
      return getCategoryById(all, id);
   }

   private Category getCategoryById(Category x, long id){
      if(x.getId() == id) {
         return x;
      }
      for(Category child : x.getChildren_data()) {
         Category x_ = getCategoryById(child, id);
         if(x_ != null) {
            return x_;
         }
      }
      return null;
   }

   public List<CategoryProduct> getProductsInCategory(long id) {
      String uri = baseUri() + "/" + relativePath4Categories + "/" + id + "/products";
      String json = getSecured(uri);

      if(!validate(json)) {
         return null;
      }

      return JSON.parseArray(json, CategoryProduct.class);
   }

   public boolean addProductToCategory(long categoryId, String productSku, int position) {
      String uri = baseUri() + "/" + relativePath4Categories + "/" + categoryId + "/products";
      Map<String, Object> req = new HashMap<>();
      Map<String, Object> detail = new HashMap<>();
      detail.put("sku", productSku);
      detail.put("position", position);
      detail.put("category_id", categoryId);
      detail.put("extension_attributes", new HashMap<>());
      req.put("productLink", detail);
      String body = JSON.toJSONString(req, SerializerFeature.BrowserCompatible);
      String json = putSecure(uri, body);

      return json.equals("true");
   }


   @Override public String token() {
      return client.token();
   }


   @Override public String baseUri() {
      return client.baseUri();
   }


   public boolean removeProductFromCategory(long categoryId, String productSku) {
      String uri = baseUri() + "/" + relativePath4Categories + "/" + categoryId + "/products/" + productSku;

      String json = deleteSecure(uri);
      return json.equals("true");
   }
}
