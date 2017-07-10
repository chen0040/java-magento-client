package com.github.chen0040.magento.models;


import java.util.HashMap;
import java.util.Map;


/**
 * Created by xschen on 10/7/2017.
 */
public class CartItem {
   private long item_id;
   private String sku;
   private int qty;
   private String name;
   private double price;
   private String product_type;
   private String quote_id;
   private Map<String, Object> product_option = new HashMap<>();
}


