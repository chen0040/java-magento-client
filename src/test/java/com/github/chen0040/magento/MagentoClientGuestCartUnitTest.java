package com.github.chen0040.magento;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.chen0040.magento.models.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;


/**
 * Created by xschen on 10/7/2017.
 */
public class MagentoClientGuestCartUnitTest {
   private static final Logger logger = LoggerFactory.getLogger(MagentoClientGuestCartUnitTest.class);

   @Test
   public void test_newCart(){
      MagentoClient client = new MagentoClient(Mediator.url);
      String cartId = client.guestCart().newCart();
      Cart cart = client.guestCart().getCart(cartId);

      logger.info("cart: \r\n{}", JSON.toJSONString(cart, SerializerFeature.PrettyFormat));
   }


}
