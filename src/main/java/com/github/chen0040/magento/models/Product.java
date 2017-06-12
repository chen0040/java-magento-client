package com.github.chen0040.magento.models;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 12/6/2017.
 */
@Getter
@Setter
public class Product {
   private long id = 1;
   private String sku = "product_dynamic_1";
   private String name =  "Simple Product 1";
   private long attribute_set_id =  4;
   private double price = 10;
   private int status  = 1;
   private int visibility = 4;
   private String type_id = "simple";
   private String created_at = "2017-05-03 03:46:13";
   private String updated_at = "2017-05-03 03:46:13";
   private double weight = 1;
   private List<MagentoAttribute> extension_attributes = new ArrayList<>();
   private List<String> product_links = new ArrayList<>();
   private List<Double> tier_prices = new ArrayList<>();
   private List<MagentoAttribute> custom_attributes = new ArrayList<>();

}
