package com.github.chen0040.magento.models;


import lombok.Getter;
import lombok.Setter;


/**
 * Created by xschen on 10/7/2017.
 */
@Getter
@Setter
public class Currency {
   private String global_currency_code = "SGD";
   private String base_currency_code = "SGD";
   private String store_currency_code = "SGD";
   private String quote_currency_code = "SGD";
   private double store_to_base_rate = 0;
   private double store_to_quote_rate = 0;
   private double base_to_global_rate = 1;
   private double base_to_quote_rate = 1;
}
