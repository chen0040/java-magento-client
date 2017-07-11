package com.github.chen0040.magento.models;


import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by xschen on 10/7/2017.
 */
@Setter
@Getter
public class CartItem {
   private long item_id;
   private String sku;
   private int qty;
   private String name;
   private double price;
   private String product_type;
   private String quote_id;

   private CartItemProductOption product_option = new CartItemProductOption();
   private Map<String, Object> extension_attributes = new HashMap<>();
}


