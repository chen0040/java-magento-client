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
public class ConfigurableItemOption {
   private String option_id = "";
   private double option_value = 0;
   private Map<String, Object> extension_attributes = new HashMap<>();
}
