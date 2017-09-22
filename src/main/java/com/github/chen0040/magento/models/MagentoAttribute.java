package com.github.chen0040.magento.models;


import lombok.Getter;
import lombok.Setter;


/**
 * Created by xschen on 12/6/2017.
 */
@Setter
@Getter
public class MagentoAttribute {
   private String attribute_code = "description";
   private Object value = "Full simple product Description 1";

   public MagentoAttribute(){

   }

   public MagentoAttribute(String attribute_code, Object value) {
      this.attribute_code = attribute_code;
      this.value = value;
   }
}
