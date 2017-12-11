# java-magento-client
Java client for communicating with Magento2 site

# Install

Add the following dependency to your POM file:

```xml
<dependency>
    <groupId>com.github.chen0040</groupId>
    <artifactId>java-magento-client</artifactId>
    <version>1.0.13</version>
</dependency>
```

# Features

* Support for token based authentication (ideal for Android or Spring application)
* Support for V1 rest api at the current version of Magento which is Magento2 version 2.16
* Allow access to:
    * Product (CRUD)
    * Product Media (CRUD)
    * Product Inventory (RU)
    * Product Categories (CRUD)
    * Guest Shopping Cart (CRUD)
    * My Shopping Cart (CRUD)
    * Account (R)


The java client provides access to web apis as listed in [link](http://devdocs.magento.com/swagger/index.html) and [link2](http://devdocs.magento.com/guides/v2.0/rest/list.html) currently available for Magent 2.16.

As Magento2 by default enable a feature preventing anonymous access to most of the [web APIs](http://devdocs.magento.com/guides/v2.0/rest/anonymous-api-security.html) which could cause third-party integrations to fail. If a third-party integration calls any of these web APIs, it will receive an authentication error instead of the expected response. In this case, you might need to disable this feature. To disable this feature, log in to the Admin panel and navigate to Stores > Configuration > Services > Magento Web API > Web API Security. Then select Yes from the Allow Anonymous Guest Access menu.

Note that if the anonymous is allowed, then the client.loginAsClient() or client.loginAsAdmin() do not need to be invoked if
the data requested (e.g., getting the categories, product listing or product detail) does not require additional permission.

# Usage

### Customer Login

The sample code below shows how to login to magento site and retrieve the current login account information:

```java
String magento_site_url = "http://magento.ll";
String username = "chen0040@change.me";
String password = "password";
MagentoClient client = new MagentoClient(magento_site_url);
String token = client.loginAsClient(username, password);
Account myAccount = client.getMyAccount();
```

### Admin Login

The sample code below shows how to login to magento site as the administrator and retrieve the admin login account information:

```java
String magento_site_url = "http://magento.ll";
String username = "admin";
String password = "admin-password";
MagentoClient client = new MagentoClient(magento_site_url);
String token = client.loginAsAdmin(username, password);
Account account = client.getAccountById(1);
```

### Logout

The line below shows how to logout:

```java
client.logout();
```

### Product

The sample code below shows how to list products, get/add/update/delete a particular product by its sku
 
```java
MagentoClient client = new MagentoClient(magento_site_url);

int pageIndex = 0;
int pageSize = 10;
ProductPage page = client.products().page(pageIndex, pageSize);
List<Product> products = page.getItems();

// check if product by sku exists
boolean exists = client.products().hasProduct(sku);

// get product detail 
Product product = client.products().getProductBySku(sku);

// action below requires admin login by default
client.loginAsAdmin(username, password);

// create or update a product 
Product product = new Product();
product.setSku("B203-SKU");
product.setName("B203");
product.setPrice(30.00);
product.setStatus(1);
product.setType_id("simple");
product.setAttribute_set_id(4);
product.setWeight(1);
product.setVisibility(Product.VISIBILITY_BOTH);
product.setStatus(Product.STATUS_ENABLED);
      
Product saveProduct = client.products().saveProduct(product);

// delete a product
client.products().deleteProduct(sku);
```

### Product Media

The sample code below shows how to list the media associated with a particular product:

```java
String productSku = "B202-SKU";
List<ProductMedia> mediaList = client.media().getProductMediaList(productSku);

// below returns a list of absoluate/relative urls for the media (e.g. images) associated with the product
List<String> imageUrls = client.media().getProductMediaAbsoluteUrls(productSku);
List<String> imageUrls = client.media().getProductMediaRelativeUrls(productSku);
```

In the above code,the entry id of a product media associated with the product can be obtained by calling the ProductMedia.getId() api.

The sample code below shows how to obtain a particular media associated with a product:

```java
String productSku = "B202-SKU";
long entryId = 1L;

ProductMedia media = client.media().getProductMedia(productSku, entryId);

// below returns a list of absoluate/relative urls for the media (e.g. images) associated with the product
String imageUrl = client.media().getProductMediaAbsoluteUrl(productSku, entryId);
String imageUrl = client.media().getProductMediaRelativeUrl(productSku, entryId);
```

The sample code below shows how to upload an image for a particular product given the bytes of the image file:

```java
String productSku = "B202-SKU";
String imageFileName = "new_image.png";

InputStream inputStream = new FileInputStream(imageFileName);

ByteArrayOutputStream baos = new ByteArrayOutputStream();
int length;
byte[] bytes = new byte[1024];
while((length = inputStream.read(bytes, 0, 1024)) > 0) {
 baos.write(bytes, 0, length);
}
bytes = baos.toByteArray();

boolean overwrite = true;
long uploadedEntryId = client.media().uploadImage(productSku, bytes, ImageType.Png, overwrite);
```

The sample code below shows how to upload an image for a particular product given the file path of the image file to upload:

```java
String productSku = "B202-SKU";
String imageFilePath = "new_image.png";

boolean overwrite = true;
long uploadedEntryId = client.media().uploadImage(productSku, imageFilePath, overwrite);
```

The uploadedEntryId returned is the entry id created for the newly uploaded image.

The sample code below shows how to update an image media for a particular product given the bytes of the new image:

```java
String productSku = "B202-SKU";
String imageFileName = "new_image.png";

InputStream inputStream = new FileInputStream(imageFileName);

ByteArrayOutputStream baos = new ByteArrayOutputStream();
int length;
byte[] bytes = new byte[1024];
while((length = inputStream.read(bytes, 0, 1024)) > 0) {
 baos.write(bytes, 0, length);
}
bytes = baos.toByteArray();
long entryId = 1L; // entry id of the media to be updated
boolean updated = client.media().updateImage(productSku, entryId, bytes, ImageType.Png);
```

The sample code below shows how to update an image media for a particular product given the file path of the new image:

```java
String productSku = "B202-SKU";
String imageFilePath = "new_image.png";
long entryId = 1L; // entry id of the media to be updated
boolean updated = client.media().updateImage(productSku, entryId, imageFilePath);
```

The sample code below shows how to delete an image or a video associated with a particular product:

```java
String productSku = "B202-SKU";
long entryId = 1L; // entry id of the media to be deleted
boolean deleted = client.media().deleteProductMedia(productSku, entryId);
```

### Product Categories

The sample code below show how to list/add/update/delete categories, get a particular category, 
 
```java
MagentoClient client = new MagentoClient(magento_site_url);

// list categories
Category page = client.categories().all();

// get the category that has category_id = 15 (Clean means no children of that category will be returned)
Category category15 = client.categories().getCategoryByIdClean(15);
Category category15 = client.categories().getCategoryByIdWithChildren(15);

// action below requires admin login by default
client.loginAsAdmin(username, password);

// delete category with category id = 15
client.categories().deleteCategory(15);

Category newCategory = ...
client.categories().addCategory(newCategory);

newCategory.setName("New Category Name");
client.categories().updateCategory(newCategory);
```

The sample code below show how to list/add/remove products under a category:

```java
// list products under category 15
List<CategoryProduct> products = client.categories().getProductsInCategory(15);

// add product to category
long categoryId = 15;
String productSku = "B202-SKU";
int position = 1;
boolean added = client.categories().addProductToCategory(categoryId, productSku, position);

// remove product from category
boolean removed = client.categories().removeProductFromCategory(categoryId, productSku);
```

### Product Inventory 

The sample code below shows how to obtain and update the inventory information for a particular product sku:
 
```java
MagentoClient client = new MagentoClient(magento_site_url);

String productSku = "product_dynamic_571";
StockItems inventory_for_sku = client.inventory().getStockItems(productSku);


// action below requires admin login by default
client.loginAsAdmin(username, password);

// to update the inventory for the product
inventory_for_sku.setQty(10);
String stockId = client.inventory().saveStockItems(productSku, inventory_for_sku);
```

### Guest Shopping Cart

The sample code below shows how to create a new guest shopping cart, add/update/delete items in the shopping cart:

Note that creating guest shopping cart does not require login

```java
MagentoClient client = new MagentoClient(magento_site_url);
String cartId = client.guestCart().newCart();

CartItem item = new CartItem();
item.setQty(1);
item.setSku("product_dynamic_758");

// add new item to shopping cart
item = client.guestCart().addItemToCart(cartId, item);
System.out.println("cartItem: " + JSON.toJSONString(item, SerializerFeature.PrettyFormat));

// update item in the shopping cart
item.setQty(3);
item = client.guestCart().updateItemInCart(cartId, item);
System.out.println("cartItem: " + JSON.toJSONString(item, SerializerFeature.PrettyFormat));

// delete item in the shopping cart
boolean deleted = client.guestCart().deleteItemInCart(cartId, item.getItem_id());

Cart cart = client.guestCart().getCart(cartId);
CartTotal cartTotal = client.getGuestCart().getCartTotal(cartId);

System.out.println("cart: " + JSON.toJSONString(cart, SerializerFeature.PrettyFormat));
System.out.println("cartTotal: " + JSON.toJSONString(cartTotal, SerializerFeature.PrettyFormat));
```

The sample code belows show how to transfer a guest cart to my cart after user login:

```bash
MagentoClient client = new MagentoClient(magento_site_url);

String cartId = client.guestCart().newCart();

CartItem item = new CartItem();
item.setQty(1);
item.setSku("product_dynamic_758");

item = client.guestCart().addItemToCart(cartId, item);

client.loginAsClient("username", "password");
boolean result = client.myCart().transferGuestCartToMyCart(cartId);

Cart cart = client.myCart().getCart();
CartTotal cartTotal = client.myCart().getCartTotal();

```

### My Shopping Cart

The sample code below shows how to create my shopping cart, add/update/delete items in the shopping cart:

Note that creating my shopping cart requires login

```java
MagentoClient client = new MagentoClient(magento_site_url);
client.loginAsClient("username", "password");
String quoteId = client.myCart().newQuote();

CartItem item = new CartItem();
item.setQty(1);
item.setSku("product_dynamic_758");

// add new item to shopping cart
item = client.myCart().addItemToCart(quoteId, item);
System.out.println("cartItem: " + JSON.toJSONString(item, SerializerFeature.PrettyFormat));

// update item in the shopping cart
item.setQty(3);
item = client.myCart().updateItemInCart(quoteId, item);
System.out.println("cartItem: " + JSON.toJSONString(item, SerializerFeature.PrettyFormat));

// delete item in the shopping cart
boolean deleted = client.myCart().deleteItemInCart(item.getItem_id());

Cart cart = client.myCart().getCart();
CartTotal cartTotal = client.myCart().getCartTotal();

System.out.println("cart: " + JSON.toJSONString(cart, SerializerFeature.PrettyFormat));
System.out.println("cartTotal: " + JSON.toJSONString(cartTotal, SerializerFeature.PrettyFormat));
```


# Notes

* http://devdocs.magento.com/guides/v2.1/howdoi/webapi/search-criteria.html
