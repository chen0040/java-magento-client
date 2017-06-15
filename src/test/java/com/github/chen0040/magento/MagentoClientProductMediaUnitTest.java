package com.github.chen0040.magento;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.chen0040.magento.models.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Created by xschen on 15/6/2017.
 */
public class MagentoClientProductMediaUnitTest {

   private static final Logger logger = LoggerFactory.getLogger(MagentoClientProductMediaUnitTest.class);


   @Test
   public void test_add_product() {
      MagentoClient client = new MagentoClient(Mediator.url);
      client.loginAsAdmin(Mediator.adminUsername, Mediator.adminPassword);

      Product newProduct = new Product();
      newProduct.setSku("B203-SKU");
      newProduct.setName("B203");
      newProduct.setPrice(30.00);
      newProduct.setStatus(1);
      newProduct.setType_id("simple");
      newProduct.setAttribute_set_id(4);
      newProduct.setWeight(1);

      logger.info("add product result: {}", JSON.toJSONString(client.products().addProduct(newProduct), SerializerFeature.PrettyFormat));
   }

   @Test
   public void test_get_product_media_list() {
      String productSku = "B202-SKU";
      MagentoClient client = new MagentoClient(Mediator.url);
      client.loginAsAdmin(Mediator.adminUsername, Mediator.adminPassword);
      logger.info("media list: \r\n{}", JSON.toJSONString(client.media().getProductMediaList(productSku), SerializerFeature.PrettyFormat));
   }

   @Test
   public void test_get_product_media_urls() {
      String productSku = "B202-SKU";
      MagentoClient client = new MagentoClient(Mediator.url);
      client.loginAsAdmin(Mediator.adminUsername, Mediator.adminPassword);
      logger.info("media absolute urls: \r\n{}", JSON.toJSONString(client.media().getProductMediaAbsoluteUrls(productSku), SerializerFeature.PrettyFormat));
      logger.info("media relative urls: \r\n{}", JSON.toJSONString(client.media().getProductMediaRelativeUrls(productSku), SerializerFeature.PrettyFormat));
   }

   @Test
   public void test_get_product_media_url() {
      String productSku = "B202-SKU";
      MagentoClient client = new MagentoClient(Mediator.url);
      client.loginAsAdmin(Mediator.adminUsername, Mediator.adminPassword);
      long entryId = 1L;
      logger.info("media absoluate url: \r\n{}", JSON.toJSONString(client.media().getProductMediaAbsoluteUrl(productSku, entryId), SerializerFeature.PrettyFormat));
      logger.info("media relative url: \r\n{}", JSON.toJSONString(client.media().getProductMediaRelativeUrl(productSku, entryId), SerializerFeature.PrettyFormat));
   }

   @Test
   public void test_get_product_media() {
      String productSku = "B202-SKU";
      MagentoClient client = new MagentoClient(Mediator.url);
      client.loginAsAdmin(Mediator.adminUsername, Mediator.adminPassword);
      long entryId = 1L;
      logger.info("media: \r\n{}", JSON.toJSONString(client.media().getProductMedia(productSku, entryId), SerializerFeature.PrettyFormat));
   }

   @Test
   public void test_delete_product_media() {
      String productSku = "B202-SKU";
      MagentoClient client = new MagentoClient(Mediator.url);
      client.loginAsAdmin(Mediator.adminUsername, Mediator.adminPassword);
      long entryId = 2L;
      logger.info("media deleted: \r\n{}", JSON.toJSONString(client.media().deleteProductMedia(productSku, entryId), SerializerFeature.PrettyFormat));
   }

   @Test
   public void test_upload_image() throws IOException {
      String productSku = "B202-SKU";

      MagentoClient client = new MagentoClient(Mediator.url);
      client.loginAsAdmin(Mediator.adminUsername, Mediator.adminPassword);

      String filename = "/m/b/mb01-blue-0.png";
      int position = 1;
      String type = "image/png";
      String imageFileName = "new_image.png";

      InputStream inputStream = MagentoClientProductUnitTest.class.getClassLoader().getResourceAsStream("sample.png");

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      int length;
      byte[] bytes = new byte[1024];
      while((length = inputStream.read(bytes, 0, 1024)) > 0) {
         baos.write(bytes, 0, length);
      }
      bytes = baos.toByteArray();
      logger.info("uploaded image id: {}", client.media().uploadProductImage(productSku, position, filename,  bytes, type, imageFileName));
   }

   @Test
   public void test_update_image() throws IOException {
      String productSku = "B202-SKU";

      MagentoClient client = new MagentoClient(Mediator.url);
      client.loginAsAdmin(Mediator.adminUsername, Mediator.adminPassword);

      String filename = "/m/b/mb01-blue-0.png";
      int position = 1;
      String type = "image/png";
      String imageFileName = "new_image.png";

      InputStream inputStream = MagentoClientProductUnitTest.class.getClassLoader().getResourceAsStream("sample.png");

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      int length;
      byte[] bytes = new byte[1024];
      while((length = inputStream.read(bytes, 0, 1024)) > 0) {
         baos.write(bytes, 0, length);
      }
      bytes = baos.toByteArray();
      long entryId = 3L;
      logger.info("image updated: {}", client.media().updateProductImage(productSku, entryId, position, filename,  bytes, type, imageFileName));
   }

}
