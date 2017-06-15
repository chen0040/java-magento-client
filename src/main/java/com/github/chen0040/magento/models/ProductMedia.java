package com.github.chen0040.magento.models;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 15/6/2017.
 */
@Getter
@Setter
public class ProductMedia {
   private long id = 1;
   private String media_type = "image";
   private String label = "Image";
   private int position = 1;
   private boolean disabled = false;
   private List<String> types = new ArrayList<>();
   private String file = "new_image.png";
}
