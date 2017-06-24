package com.github.chen0040.magento.services;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.chen0040.magento.MagentoClient;
import com.github.chen0040.magento.enums.ImageType;
import com.github.chen0040.magento.models.Product;
import com.github.chen0040.magento.models.ProductMedia;
import com.github.chen0040.magento.models.ProductPage;
import com.github.chen0040.magento.utils.StringUtils;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.function.Function;


/**
 * Created by xschen on 15/6/2017.
 */
public class MagentoProductMediaManager extends MagentoHttpComponent {

   private static final Logger logger = LoggerFactory.getLogger(MagentoProductMediaManager.class);

   private MagentoClient client;


   public MagentoProductMediaManager(MagentoClient client) {
      this.client = client;
   }


   @Override public String token() {
      return client.token();
   }


   @Override public String baseUri() {
      return client.baseUri();
   }


   public long uploadProductImage(String sku, int position, String filename, byte[] imageBytes, String imageType, String imageFileName) {

      try {
         String base64EncodedData = new String(Base64.encodeBase64(imageBytes), "UTF-8");
         return uploadProductImage(sku, position, filename, base64EncodedData, imageType, imageFileName);
      }
      catch (UnsupportedEncodingException e) {
         logger.error("Failed to covert image bytes to base64 string", e);
      }
      return -1L;
   }


   public boolean updateProductImage(String sku, long entryId, int position, String filename, byte[] imageBytes, String imageType, String imageFileName) {

      try {
         String base64EncodedData = new String(Base64.encodeBase64(imageBytes), "UTF-8");
         return updateProductImage(sku, entryId, position, filename, base64EncodedData, imageType, imageFileName);
      }
      catch (UnsupportedEncodingException e) {
         logger.error("Failed to covert image bytes to base64 string", e);
      }
      return false;
   }


   public boolean updateProductImage(String sku, long entryId, int position, String filename, String base64EncodedData, String imageType, String imageFileName) {
      String uri = baseUri() + "/rest/V1/products/" + escape(sku) + "/media/" + entryId;

      Map<String, Object> req = new HashMap<>();
      Map<String, Object> entry = new HashMap<>();

      entry.put("media_type", "image");
      entry.put("id", entryId);
      entry.put("label", "Image");
      entry.put("position", position);
      entry.put("disabled", false);
      List<String> types = Arrays.asList("image", "small_image", "thumbnail");
      entry.put("types", types);
      entry.put("file", filename);
      Map<String, Object> content = new HashMap<>();

      content.put("base64_encoded_data", base64EncodedData);
      content.put("type", imageType);
      content.put("name", imageFileName);

      entry.put("content", content);

      req.put("entry", entry);
      String body = JSON.toJSONString(req, SerializerFeature.BrowserCompatible);
      String json = putSecure(uri, body);

      if (!validate(json)) {
         return false;
      }
      return json.equalsIgnoreCase("true");
   }


   public long uploadProductImage(String sku, int position, String filename, String base64EncodedData, String imageType, String imageFileName) {
      String uri = baseUri() + "/rest/V1/products/" + escape(sku) + "/media";

      Map<String, Object> req = new HashMap<>();
      Map<String, Object> entry = new HashMap<>();

      entry.put("media_type", "image");
      entry.put("label", "Image");
      entry.put("position", position);
      entry.put("disabled", false);
      List<String> types = Arrays.asList("image", "small_image", "thumbnail");
      entry.put("types", types);
      entry.put("file", filename);
      Map<String, Object> content = new HashMap<>();

      content.put("base64_encoded_data", base64EncodedData);
      content.put("type", imageType);
      content.put("name", imageFileName);

      entry.put("content", content);

      req.put("entry", entry);
      String body = JSON.toJSONString(req, SerializerFeature.BrowserCompatible);
      String json = postSecure(uri, body);

      if (!validate(json)) {
         return -1L;
      }
      return Long.parseLong(StringUtils.stripQuotation(json));
   }


   public List<ProductMedia> getProductMediaList(String sku) {
      String uri = baseUri() + "/rest/V1/products/" + escape(sku) + "/media";

      String json = getSecured(uri);

      if (!validate(json)) {
         return null;
      }

      return JSON.parseArray(json, ProductMedia.class);
   }


   public ProductMedia getProductMedia(String sku, long entryId) {

      String uri = baseUri() + "/rest/V1/products/" + escape(sku) + "/media/" + entryId;

      String json = getSecured(uri);

      if (!validate(json)) {
         return null;
      }

      return JSON.parseObject(json, ProductMedia.class);
   }


   public boolean deleteProductMedia(String sku, long entryId) {

      String uri = baseUri() + "/rest/V1/products/" + escape(sku) + "/media/" + entryId;

      String json = deleteSecure(uri);

      if (!validate(json)) {
         return false;
      }

      return json.equalsIgnoreCase("true");
   }


   public String getProductMediaAbsoluteUrl(String sku, long entryId) {
      ProductMedia media = getProductMedia(sku, entryId);
      String filename = media.getFile();
      return baseUri() + "/pub/media/catalog/product/" + filename;
   }


   public String getProductMediaRelativeUrl(String sku, long entryId) {
      ProductMedia media = getProductMedia(sku, entryId);
      String filename = media.getFile();
      return "/pub/media/catalog/product/" + filename;
   }


   public List<String> getProductMediaAbsoluteUrls(String sku) {
      List<ProductMedia> mediaList = getProductMediaList(sku);
      List<String> result = new ArrayList<>();
      for (ProductMedia media : mediaList) {
         String filename = media.getFile();
         String uri = baseUri() + "/pub/media/catalog/product/" + filename;
         result.add(uri);
      }
      return result;
   }


   public List<String> getProductMediaRelativeUrls(String sku) {
      List<ProductMedia> mediaList = getProductMediaList(sku);
      List<String> result = new ArrayList<>();
      for (ProductMedia media : mediaList) {
         String filename = media.getFile();
         String uri = "/pub/media/catalog/product/" + filename;
         result.add(uri);
      }
      return result;
   }

   public long uploadImage(String sku, String imageFilePath, boolean forceOverwrite) {

      ImageType imageType = imageFilePath.toLowerCase().endsWith(".png") ? ImageType.Png : ImageType.Jpeg;

      List<ProductMedia> mediaList = getProductMediaList(sku);

      if (forceOverwrite) {
         for (int k = 0; k < mediaList.size(); ++k) {
            deleteProductMedia(sku, mediaList.get(k).getId());
         }
      }
      else {
         if (mediaList.size() > 0) {
            return -1;
         }
      }



      String filename = "/m/b/mb-" + StringUtils.cleanup(sku) + ".png";
      int position = 1;
      String type = "image/png";

      if (imageType == ImageType.Jpeg) {
         type = "image/jpeg";
      }

      String imageName = imageFilePath;
      if (imageFilePath.contains(File.separator)) {
         imageName = imageFilePath.substring(imageFilePath.lastIndexOf(File.separator) + 1, imageFilePath.length());
      }

      if (imageFilePath.contains("/")) {
         imageName = imageFilePath.substring(imageFilePath.lastIndexOf("/") + 1, imageFilePath.length());
      }

      try {
         InputStream inputStream = new FileInputStream(imageFilePath);

         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         int length;
         byte[] bytes = new byte[1024];
         while ((length = inputStream.read(bytes, 0, 1024)) > 0) {
            baos.write(bytes, 0, length);
         }
         bytes = baos.toByteArray();

         long uploadedId = client.media().uploadProductImage(sku, position, filename, bytes, type, imageName);

         logger.info("uploaded {} for product {}: {}", imageFilePath, sku, uploadedId);

         return uploadedId;
      }
      catch (IOException exception) {
         logger.error("Failed to upload as image " + imageFilePath + " is not available.", exception);
      }

      return -1;
   }

   public long uploadImage(String sku, byte[] bytes, ImageType imageType, boolean forceOverwrite) {

      List<ProductMedia> mediaList = getProductMediaList(sku);

      if (forceOverwrite) {
         for (int k = 0; k < mediaList.size(); ++k) {
            deleteProductMedia(sku, mediaList.get(k).getId());
         }
      }
      else {
         if (mediaList.size() > 0) {
            return -1;
         }
      }



      String filename = "/m/b/mb-" + StringUtils.cleanup(sku) + ".png";
      int position = 1;
      String type = "image/png";

      if (imageType == ImageType.Jpeg) {
         type = "image/jpeg";
      }

      String imageName = filename;

      if (filename.contains("/")) {
         imageName = filename.substring(filename.lastIndexOf("/") + 1, filename.length());
      }

      long uploadedId = uploadProductImage(sku, position, filename, bytes, type, imageName);

      logger.info("uploaded {} for product {}: {}", filename, sku, uploadedId);

      return uploadedId;
   }

   public boolean updateImage(String sku, long entryId, String imageFilePath) {

      ImageType imageType = imageFilePath.toLowerCase().endsWith(".png") ? ImageType.Png : ImageType.Jpeg;


      String filename = "/m/b/mb-" + StringUtils.cleanup(sku) + ".png";
      int position = 1;
      String type = "image/png";

      if (imageType == ImageType.Jpeg) {
         type = "image/jpeg";
      }

      String imageName = imageFilePath;
      if (imageFilePath.contains(File.separator)) {
         imageName = imageFilePath.substring(imageFilePath.lastIndexOf(File.separator) + 1, imageFilePath.length());
      }

      if (imageFilePath.contains("/")) {
         imageName = imageFilePath.substring(imageFilePath.lastIndexOf("/") + 1, imageFilePath.length());
      }

      try {
         InputStream inputStream = new FileInputStream(imageFilePath);

         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         int length;
         byte[] bytes = new byte[1024];
         while ((length = inputStream.read(bytes, 0, 1024)) > 0) {
            baos.write(bytes, 0, length);
         }
         bytes = baos.toByteArray();

         boolean updated = updateProductImage(sku, entryId, position, filename, bytes, type, imageName);

         logger.info("updating {} for product {}: {}", imageFilePath, sku, updated);

         return updated;
      }
      catch (IOException exception) {
         logger.error("Failed to upload as image " + imageFilePath + " is not available.", exception);
      }

      return false;
   }

   public boolean updateImage(String sku, long entryId, byte[] bytes, ImageType imageType) {

      String filename = "/m/b/mb-" + StringUtils.cleanup(sku) + ".png";
      int position = 1;
      String type = "image/png";

      if (imageType == ImageType.Jpeg) {
         type = "image/jpeg";
      }

      String imageName = filename;

      if (filename.contains("/")) {
         imageName = filename.substring(filename.lastIndexOf("/") + 1, filename.length());
      }

      boolean updated = updateProductImage(sku, entryId, position, filename, bytes, type, imageName);

      logger.info("uploaded {} for product {}: {}", filename, sku, updated);

      return updated;
   }
}
