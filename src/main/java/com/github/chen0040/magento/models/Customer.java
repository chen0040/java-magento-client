package com.github.chen0040.magento.models;


import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by xschen on 10/7/2017.
 */
@Getter
@Setter
public class Customer {
   private String email = null;
   private String firstname = null;
   private String lastname = null;
   private Address billing_address = new Address();
   private long orig_order_id = 0;
   private Currency currency = new Currency();
   private boolean customer_is_guest = false;
   private boolean customer_note_notify = true;
   private long customer_tax_class_id = 3;
   private long store_id = 1;
   private Map<String, Object> extension_attributes = new HashMap<>();
}
