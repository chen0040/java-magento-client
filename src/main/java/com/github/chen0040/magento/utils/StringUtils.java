package com.github.chen0040.magento.utils;


/**
 * Created by xschen on 12/6/2017.
 */
public class StringUtils {
   public static String stripQuotation(String s) {
      if(s.startsWith("\"")) {
         s = s.substring(1, s.length());
      }
      if(s.endsWith("\"")) {
         s = s.substring(0, s.length()-1);
      }
      return s;
   }
}
