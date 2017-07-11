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
 * Created by xschen on 10/7/2017.
 */
public class MagentoGuestCartManager extends MagentoHttpComponent {

   protected String relativePath = "rest/V1/guest-carts";
   private final MagentoClient client;

   public MagentoGuestCartManager(MagentoClient client){
      super(client.getHttpComponent());
      this.client = client;
   }

   @Override public String token() {
      return client.token();
   }


   @Override public String baseUri() {
      return client.baseUri();
   }

   public String newCart() {
      String json = postSecure(baseUri() + "/" + relativePath, "");

      if(!validate(json)){
         return null;
      }

      return StringUtils.stripQuotation(json);
   }

   public Cart getCart(String cartId) {

      String json = getSecured(baseUri() + "/" + relativePath + "/" + cartId);

      if(!validate(json)){
         return null;
      }

      System.out.println(json);

      Cart cart = JSON.parseObject(json, Cart.class);
      return cart;
   }

   public CartTotal getCartTotal(String cartId) {
      String json = getSecured(baseUri() + "/" + relativePath + "/" + cartId + "/totals");

      if(!validate(json)){
         return null;
      }

      System.out.println(json);

      CartTotal cartTotal = JSON.parseObject(json, CartTotal.class);
      return cartTotal;
   }

   public CartItem addItemToCart(String cartId, CartItem item) {
      Map<String, Map<String, Object>> request = new HashMap<>();
      Map<String, Object> cartItem = new HashMap<>();
      cartItem.put("qty", item.getQty());
      cartItem.put("sku", item.getSku());
      cartItem.put("quote_id", cartId);
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

   public CartItem updateItemInCart(String cartId, CartItem item) {
      Map<String, Map<String, Object>> request = new HashMap<>();
      Map<String, Object> cartItem = new HashMap<>();
      cartItem.put("qty", item.getQty());
      cartItem.put("sku", item.getSku());
      cartItem.put("quote_id", cartId);
      cartItem.put("item_id", item.getItem_id());
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

   public boolean deleteItemInCart(String cartId, int itemId) {
      String json = deleteSecure(baseUri() + "/" + relativePath + "/" + cartId + "/items/" + itemId);

      if(!validate(json)){
         return false;
      }

      System.out.println(json);

      return json.equalsIgnoreCase("true");
   }

}
