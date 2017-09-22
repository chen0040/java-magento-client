package com.github.chen0040.magento.services;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.chen0040.magento.MagentoClient;
import com.github.chen0040.magento.models.*;
import com.github.chen0040.magento.utils.StringUtils;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by xschen on 12/6/2017.
 */
public class MagentoProductManager extends MagentoHttpComponent {

   private static final Logger logger = LoggerFactory.getLogger(MagentoProductManager.class);
   private MagentoClient client;
   private static final String relativePath4Products = "rest/V1/products";

   public MagentoProductManager(MagentoClient client) {
      super(client.getHttpComponent());
      this.client = client;
   }

   @Override public String token() {
      return client.token();
   }


   @Override public String baseUri() {
      return client.baseUri();
   }


   public ProductPage page(int pageIndex, int pageSize) {
      String uri = baseUri() + "/" + relativePath4Products
              + "?searchCriteria[currentPage]=" + pageIndex
              + "&searchCriteria[pageSize]=" + pageSize;
      String json = getSecured(uri);
      if(!validate(json)){
         return null;
      }

      return JSON.parseObject(json, ProductPage.class);
   }



   public Product getProductBySku(String sku) {
      String uri = baseUri() + "/" + relativePath4Products + "/" + escape(sku);
      String json = getSecured(uri);

      if(!validate(json)){
         return null;
      }

      System.out.println(json);

      return JSON.parseObject(json, Product.class);
   }





   public List<MagentoAttributeType> getProductAttributeTypes() {
      String uri = baseUri() + "/rest/V1/products/attributes/types";

      String json = getSecured(uri);

      if(!validate(json)) {
         return null;
      }

      return JSON.parseArray(json, MagentoAttributeType.class);
   }

   public ProductAttributePage getProductAttributes(int pageIndex, int pageSize){
      String uri = baseUri() + "/rest/V1/products/attributes"
              + "?searchCriteria[currentPage]=" + pageIndex
              + "&searchCriteria[pageSize]=" + pageSize;

      String json = getSecured(uri);

      if(!validate(json)) {
         return null;
      }

      return JSON.parseObject(json, ProductAttributePage.class);

   }

   public boolean hasProduct(String sku) {
      return getProductBySku(sku) != null;
   }

   public Product saveProduct(Product product){
      String sku = product.getSku();
      String url = baseUri() + "/" + relativePath4Products + "/" + escape(sku);

      Map<String, Object> detail = new HashMap<>();

      detail.put("sku", product.getSku());
      detail.put("name", product.getName());
      detail.put("price", product.getPrice());
      detail.put("status", product.getStatus());
      detail.put("type_id", product.getType_id());
      detail.put("attribute_set_id", product.getAttribute_set_id());
      detail.put("weight", product.getWeight());
      detail.put("visibility", product.getVisibility());
      detail.put("status", product.getStatus());

      Map<String, Object> req = new HashMap<>();
      req.put("product", detail);

      String body = JSON.toJSONString(req, SerializerFeature.PrettyFormat);
      logger.info("posting:\r\n{}", body);
      String json = putSecure(url, body);

      if(!validate(json)){
         return null;
      }

      return JSON.parseObject(json, Product.class);
   }




   public String page(String name, String value, String condition_type) {
      String uri = baseUri() + "/" + relativePath4Products
              + "?searchCriteria[filter_groups][0][filters][0][field]=category_gear"
              + "&searchCriteria[filter_groups][0][filters][0][value]=86"
              + "&searchCriteria[filter_groups][0][filters][0][condition_type]=finset";
      return getSecured(uri);
   }

   public List<MagentoType> listProductTypes() {
      String uri = baseUri() + "/rest/V1/products/types"
              + "?searchCriteria[currentPage]=0"
              + "&searchCriteria[pageSize]=1000";
      String json = getSecured(uri);
      return JSON.parseArray(json, MagentoType.class);
   }

   public List<MagentoType> listProductTypes(int page, int pageSize) {
      String uri = baseUri() + "/rest/V1/products/types"
              + "?searchCriteria[currentPage]=" + page
              + "&searchCriteria[pageSize]=" + pageSize;
      String json = getSecured(uri);
      return JSON.parseArray(json, MagentoType.class);
   }

   public String deleteProduct(String sku) {
      String url = baseUri() + "/" + relativePath4Products + "/" + escape(sku);
      return deleteSecure(url);
   }


}
