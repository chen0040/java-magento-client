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
public class CartItemProductCustomOption {

   private String option_id = "";
   private String option_value = "";
   private CartItemProductCustomOptionExtensionAttributes extension_attributes = new CartItemProductCustomOptionExtensionAttributes();

}
