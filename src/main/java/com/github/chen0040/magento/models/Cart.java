package com.github.chen0040.magento.models;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 10/7/2017.
 */
@Getter
@Setter
public class Cart {
   private long id = 93;
   private String created_at = "2017-07-10 09:51:18";
   private String updated_at = "0000-00-00 00:00:00";
   private boolean is_active = true;
   private boolean is_virtual = false;
   private List<CartItem> items = new ArrayList<>();
   private int items_count = 0;
   private int items_qty = 0;
   private Customer customer = new Customer();
}
