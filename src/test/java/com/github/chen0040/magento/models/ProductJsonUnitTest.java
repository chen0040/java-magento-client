package com.github.chen0040.magento.models;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProductJsonUnitTest {

    private static final Logger logger = LoggerFactory.getLogger(ProductJsonUnitTest.class);

    @Test
    public void testJsonDeserialization() {
        String json = readStream(ProductJsonUnitTest.class.getClassLoader().getResourceAsStream("product.json"));
        logger.info("json: {}", json);
        Product product = JSON.parseObject(json, Product.class);

        logger.info("sku: {}", product.getSku());
        for(MagentoAttribute ma : product.getCustom_attributes()){
            logger.info("custom attribute: key = {}, value = {}", ma.getAttribute_code(), ma.getValue());
        }
        for(MagentoAttribute ma : product.getExtension_attributes()){
            logger.info("extension attribute: key = {}, value = {}", ma.getAttribute_code(), ma.getValue());
        }
    }

    private String readStream(InputStream is) {
        StringBuilder sb = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(is))){
            String line;
            while((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }catch(IOException ioex){
            logger.error("Failed to read stream", ioex);
        }
        return sb.toString();
    }

}
