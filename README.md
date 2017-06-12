# java-magento-client
Java client for communicating with Magento2 site

# Features

The java client provides access to web apis as listed in [link](http://devdocs.magento.com/guides/v2.1/howdoi/webapi/search-criteria.html) currently available for Magent 2.16.

As Magento2 by default enable a feature preventing anonymous access to most of the [web APIs](http://devdocs.magento.com/guides/v2.0/rest/anonymous-api-security.html) which could cause third-party integrations to fail. If a third-party integration calls any of these web APIs, it will receive an authentication error instead of the expected response. In this case, you might need to disable this feature. To disable this feature, log in to the Admin panel and navigate to Stores > Configuration > Services > Magento Web API > Web API Security. Then select Yes from the Allow Anonymous Guest Access menu.

# Usage

The sample code below shows how to login to magento site and retrieve the current login account information:

```java
String magento_site_url = "http://magento.ll";
String username = "chen0040@change.me";
String password = "password";
MagentoClient client = new MagentoClient(magento_site_url);
String token = client.loginAsClient(username, password);
logger.info("my account: {}", client.getMyAccount());
```


The sample code below shows how to login to magento site as the administrator and retrieve the admin login account information:

```java
String magento_site_url = "http://magento.ll";
String username = "admin";
String password = "admin-password";
MagentoClient client = new MagentoClient(magento_site_url);
String token = client.loginAsAdmin(username, password);
logger.info("account with id 1: {}", client.getAccountById(1));
```
