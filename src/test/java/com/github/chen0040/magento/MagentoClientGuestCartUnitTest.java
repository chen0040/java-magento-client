package com.github.chen0040.magento;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.chen0040.magento.models.Cart;
import com.github.chen0040.magento.models.CartItem;
import com.github.chen0040.magento.models.CartTotal;
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
      CartTotal cartTotal = client.getGuestCart().getCartTotal(cartId);

      logger.info("cart: \r\n{}", JSON.toJSONString(cart, SerializerFeature.PrettyFormat));
      logger.info("cartTotal: \r\n{}", JSON.toJSONString(cartTotal, SerializerFeature.PrettyFormat));
   }

   @Test
   public void test_addItemToCart(){
      MagentoClient client = new MagentoClient(Mediator.url);
      String cartId = client.guestCart().newCart();

      CartItem item = new CartItem();
      item.setName("Simple Product 758");
      item.setQty(1);
      item.setSku("product_dynamic_758");
      item.setProduct_type("simple");
      item.setPrice(10.0);
      item.setQuote_id(cartId);

      item = client.guestCart().addItemToCart(cartId, item);


      Cart cart = client.guestCart().getCart(cartId);
      CartTotal cartTotal = client.getGuestCart().getCartTotal(cartId);

      logger.info("cartItem: \r\n{}", JSON.toJSONString(item, SerializerFeature.PrettyFormat));
      logger.info("cart: \r\n{}", JSON.toJSONString(cart, SerializerFeature.PrettyFormat));
      logger.info("cartTotal: \r\n{}", JSON.toJSONString(cartTotal, SerializerFeature.PrettyFormat));
   }


}
