package com.github.chen0040.magento.models;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 11/7/2017.
 */
@Getter
@Setter
public class CartTotal {
   private double grand_total= 0;
   private double base_grand_total= 0;
   private double subtotal= 0;
   private double base_subtotal= 0;
   private double discount_amount= 0;
   private double base_discount_amount= 0;
   private double subtotal_with_discount= 0;
   private double base_subtotal_with_discount= 0;
   private double shipping_amount= 0;
   private double base_shipping_amount= 0;
   private double shipping_discount_amount= 0;
   private double base_shipping_discount_amount= 0;
   private double tax_amount= 0;
   private double base_tax_amount= 0;
   private double weee_tax_applied_amount= 0;
   private double shipping_tax_amount= 0;
   private double base_shipping_tax_amount= 0;
   private double subtotal_incl_tax= 0;
   private double base_subtotal_incl_tax= 0;
   private double shipping_incl_tax= 0;
   private double base_shipping_incl_tax= 0;
   private String base_currency_code= "" ;
   private String quote_currency_code= "" ;
   private String coupon_code= "" ;
   private int items_qty= 0;
   private List<CartTotalItem> items = new ArrayList<>();
   private List<CartTotalSegment> total_segments = new ArrayList<>();
   private CartTotalExtensionAttributes extension_attributes = new CartTotalExtensionAttributes();
}
