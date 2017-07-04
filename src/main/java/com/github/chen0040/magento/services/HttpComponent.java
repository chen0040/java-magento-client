package com.github.chen0040.magento.services;


import java.util.Map;


/**
 * Created by xschen on 4/7/2017.
 */
public interface HttpComponent {
   String post(String url, String body, Map<String, String> headers);

   String put(String url, String body, Map<String, String> headers);

   String delete(String url, Map<String, String> headers);

   String get(String uri, Map<String, String> headers);
}
