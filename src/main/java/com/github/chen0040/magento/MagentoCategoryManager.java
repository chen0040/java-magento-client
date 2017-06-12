package com.github.chen0040.magento;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
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
