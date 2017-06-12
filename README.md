# java-magento-client
Java client for communicating with Magento2 site

# Features

The java client provides access to web apis as listed in [link](http://devdocs.magento.com/guides/v2.1/howdoi/webapi/search-criteria.html) currently available for Magent 2.16.

As Magento2 by default enable a feature preventing anonymous access to most of the [web APIs](http://devdocs.magento.com/guides/v2.0/rest/anonymous-api-security.html) which could cause third-party integrations to fail. If a third-party integration calls any of these web APIs, it will receive an authentication error instead of the expected response. In this case, you might need to disable this feature. To disable this feature, log in to the Admin panel and navigate to Stores > Configuration > Services > Magento Web API > Web API Security. Then select Yes from the Allow Anonymous Guest Access menu.

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

### Product Management (Admin)

The sample code below shows how to list products, get/add/update/delete a particular product by its sku
 
```java
MagentoClient client = new MagentoClient(magento_site_url);
client.loginAsAdmin(username, password);

int pageIndex = 0;
int pageSize = 10;
ProductPage page = client.products().page(pageIndex, pageSize);
List<Product> products = page.getItems();

// check if product by sku exists
boolean exists = client.products().hasProduct(sku);

// get product detail 
Product product = client.products().getProductBySku(sku);

// create or update a product 
Product newProduct = new Product();
newProduct.setSku("B203-SKU");
newProduct.setName("B203");
newProduct.setPrice(30.00);
newProduct.setStatus(1);
newProduct.setType_id("simple");
newProduct.setAttribute_set_id(4);
newProduct.setWeight(1);
Product saveProduct = client.products().addProduct(newProduct);

// delete a product
client.products().deleteProduct(sku);
```

### Category Management

The sample code below show how to list categories, get a particular category, or list/add/remove products under a category
 
```java
MagentoClient client = new MagentoClient(magento_site_url);
client.loginAsAdmin(username, password);

// list categories
Category page = client.categories().page(0, 10);

// get the category that has id = 15
Category category15 = client.categories().getCategoryById(15);

// list products under category 15
List<CategoryProduct> products = client.categories().getProductsInCategory(15);
```

# Notes

* http://devdocs.magento.com/guides/v2.1/howdoi/webapi/search-criteria.html
