package com.github.chen0040.magento.models;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by xschen on 12/6/2017.
 */
@Getter
@Setter
public class Account {
   private long id = 21;
   private long group_id = 1;
   private Date created_at;
   private Date updated_at;
   private String created_in = "Store view 1 - website_id_1 - group_id_1";
   private String email = "xs0040@gmail.com";
   private String firstname = "Xianshun";
   private String lastname = "Chen";
   private long store_id  = 1;
   private long website_id = 1;
   private List<Address> addresses = new ArrayList<>();
   private int disable_auto_group_change = 0;
}
