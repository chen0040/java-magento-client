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
public class Address {
   private long id = 185;
   private String region = null;
   private String region_id = null;
   private String region_code = null;
   private String country_id = null;
   private List<String> street = new ArrayList<>();

   private String telephone  = null;
   private String postcode = null;
   private String city = null;
   private String firstname = null;
   private String lastname = null;
   private String email = null;
   private int same_as_billing = 0;
   private int save_in_address_book = 0;
}
