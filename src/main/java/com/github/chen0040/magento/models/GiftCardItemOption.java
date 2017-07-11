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
public class GiftCardItemOption {
   private String giftcard_amount = "";
   private double custom_giftcard_amount = 0;
   private String giftcard_sender_name = "";
   private String giftcard_recipient_name = "";
   private String giftcard_sender_email = "";
   private String giftcard_recipient_email = "";
   private String giftcard_message = "";
   private Map<String, Object> extension_attributes =new HashMap<>();
}
