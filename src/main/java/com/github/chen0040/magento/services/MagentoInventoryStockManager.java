package com.github.chen0040.magento.services;


import com.github.chen0040.magento.MagentoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by xschen on 12/6/2017.
 */
public class MagentoInventoryStockManager extends MagentoHttpComponent {
   private static final String relativePath4InventoryStock = "rest/V1/stockItems";
   private static final Logger logger = LoggerFactory.getLogger(MagentoInventoryStockManager.class);
   private MagentoClient client;

   public MagentoInventoryStockManager(MagentoClient client) {
      this.client = client;
   }

   @Override public String token() {
      return client.token();
   }


   @Override public String baseUri() {
      return client.baseUri();
   }
}
