package com.github.chen0040.magento.services;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.chen0040.magento.MagentoClient;
import com.github.chen0040.magento.models.Cart;
import com.github.chen0040.magento.models.CartItem;
import com.github.chen0040.magento.models.CartTotal;
import com.github.chen0040.magento.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by xschen on 11/7/2017.
 */
public class MagentoMyCartManager extends MagentoHttpComponent {
   protected final MagentoClient client;
   private static final String relativePath = "rest/V1/carts";
   private static final String cartId = "mine";

   public MagentoMyCartManager(MagentoClient client) {
      super(client.getHttpComponent());
      this.client = client;

   }


   @Override public String token() {
      return client.token();
   }


   @Override public String baseUri() {
      return client.baseUri();
   }

   public String newQuote() {
      String json = postSecure(baseUri() + "/" + relativePath + "/" + cartId, "");

      if(!validate(json)){
         return null;
      }

      return StringUtils.stripQuotation(json);
   }

   public Cart getCart() {

      String json = getSecured(baseUri() + "/" + relativePath + "/" + cartId);

      if(!validate(json)){
         return null;
      }

      System.out.println(json);

      Cart cart = JSON.parseObject(json, Cart.class);
      return cart;
   }

   public CartTotal getCartTotal() {
      String json = getSecured(baseUri() + "/" + relativePath + "/" + cartId + "/totals");

      if(!validate(json)){
         return null;
      }

      System.out.println(json);

      CartTotal cartTotal = JSON.parseObject(json, CartTotal.class);
      return cartTotal;
   }

   public CartItem addItemToCart(String quoteId, CartItem item) {
      Map<String, Map<String, Object>> request = new HashMap<>();
      Map<String, Object> cartItem = new HashMap<>();
      cartItem.put("qty", item.getQty());
      cartItem.put("sku", item.getSku());
      cartItem.put("quote_id", quoteId);
      request.put("cartItem", cartItem);
      String json = JSON.toJSONString(request, SerializerFeature.BrowserCompatible);
      json = postSecure(baseUri() + "/" + relativePath + "/" + cartId + "/items", json);

      if(!validate(json)){
         return null;
      }

      System.out.println(json);

      CartItem saved = JSON.parseObject(json, CartItem.class);

      return saved;
   }

   public CartItem updateItemInCart(String quoteId, CartItem item) {
      Map<String, Map<String, Object>> request = new HashMap<>();
      Map<String, Object> cartItem = new HashMap<>();
      cartItem.put("qty", item.getQty());
      cartItem.put("sku", item.getSku());
      cartItem.put("item_id", item.getItem_id());
      cartItem.put("quote_id", quoteId);
      request.put("cartItem", cartItem);
      String json = JSON.toJSONString(request, SerializerFeature.BrowserCompatible);
      json = putSecure(baseUri() + "/" + relativePath + "/" + cartId + "/items/" + item.getItem_id(), json);

      if(!validate(json)){
         return null;
      }

      System.out.println(json);

      CartItem saved = JSON.parseObject(json, CartItem.class);

      return saved;
   }

   public boolean deleteItemInCart(int itemId) {

      String json = deleteSecure(baseUri() + "/" + relativePath + "/" + cartId + "/items/" + itemId);

      if(!validate(json)){
         return false;
      }

      System.out.println(json);

      return json.equalsIgnoreCase("true");
   }

}
