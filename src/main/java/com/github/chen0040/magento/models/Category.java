package com.github.chen0040.magento.models;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 12/6/2017.
 */
@Setter
@Getter
public class Category {
   private long id = 2;
   private long parent_id = 1;
   private String name = "Category Store Group 1 - website_id_1";
   private boolean is_active = true;
   private int position = 1;
   private int level = 1;
   private int product_count = 25;
   private List<Category> children_data = new ArrayList<>();
}
