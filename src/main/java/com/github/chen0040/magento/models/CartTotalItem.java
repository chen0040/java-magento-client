package com.github.chen0040.magento.models;


import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by xschen on 11/7/2017.
 */
@Getter
@Setter
public class CartTotalItem {
   private long item_id = 0;
   private double price = 0;
   private double base_price = 0;
   private int qty = 0;
   private double row_total = 0;
   private double base_row_total = 0;
   private double row_total_with_discount = 0;
   private double tax_amount = 0;
   private double base_tax_amount = 0;
   private double tax_percent = 0;
   private double discount_amount = 0;
   private double base_discount_amount = 0;
   private double discount_percent = 0;
   private double price_incl_tax = 0;
   private double base_price_incl_tax = 0;
   private double row_total_incl_tax = 0;
   private double base_row_total_incl_tax = 0;
   private String options = "" ;
   private double weee_tax_applied_amount = 0;
   private String weee_tax_applied = "" ;
   private Map<String, Object> extension_attributes = new HashMap<>();
   private String name = "";
}
