package com.github.chen0040.magento.models;


import lombok.Getter;
import lombok.Setter;


/**
 * Created by xschen on 11/7/2017.
 */
@Getter
@Setter
public class FileInfo {
   private String base64_encoded_data = "";
   private String type = "";
   private String name = "";
}
