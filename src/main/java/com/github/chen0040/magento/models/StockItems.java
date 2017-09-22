package com.github.chen0040.magento.models;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;


/**
 * Created by xschen on 15/6/2017.
 */
@Setter
@Getter
public class StockItems {
   private long item_id = 0;
   private long product_id = 0;
   private long stock_id = 0;
   private int qty = 0;
   private boolean is_in_stock = false;
   private boolean is_qty_decimal = false;
   private boolean show_default_notification_message = false;
   private boolean use_config_min_qty = true;
   private int min_qty = 0;
   private int use_config_min_sale_qty = 1;
   private int min_sale_qty = 1;
   private boolean use_config_max_sale_qty = true;
   private int max_sale_qty = 10000;
   private boolean use_config_backorders = true;
   private int backorders = 0;
   private boolean use_config_notify_stock_qty = true;
   private int notify_stock_qty = 1;
   private boolean use_config_qty_increments = true;
   private int qty_increments = 0;
   private boolean use_config_enable_qty_inc = true;
   private boolean enable_qty_increments = false;
   private boolean use_config_manage_stock = true;
   private boolean manage_stock =true;
   private Date low_stock_date = null;
   private boolean is_decimal_divided = false;
   private int stock_status_changed_auto = 0;
}
