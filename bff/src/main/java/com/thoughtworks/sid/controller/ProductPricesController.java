package com.thoughtworks.sid.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.sid.constant.Services;
import com.thoughtworks.sid.domain.Product;
import com.thoughtworks.sid.domain.ProductPrice;
import com.thoughtworks.sid.service.GatewayService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.rmi.PortableRemoteObject;
import java.io.IOException;

@RestController
@RequestMapping(value = "/products/{productId}/prices")
public class ProductPricesController {

    @Autowired
    private GatewayService gatewayService;

    @Autowired
    private ObjectMapper objectMapper;

    @ApiOperation(value = "创建产品价格")
    @RequestMapping(value = "/",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createProductPrice(@ApiParam(required = true, name = "productId", value = "productId") @PathVariable String productId,
                                             @RequestBody ProductPrice productPrice) {
        Product product = retrieveProduct(productId);

        if (product == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return gatewayService.post(Services.PRICE_SERVICE, "/api/product-prices/", productPrice);
        }
    }

    @ApiOperation(value = "获取产品价格列表")
    @RequestMapping(value = "/",
            method = RequestMethod.GET)
    public ResponseEntity getProductPricesList(@ApiParam(required = true, name = "productId", value = "productId") @PathVariable String productId) {
        Product product = retrieveProduct(productId);

        if (product == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return gatewayService.get(Services.PRICE_SERVICE, "/api/product-prices/?productId=" + productId);
        }
    }

    @ApiOperation(value = "获取产品价格")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getProductPrice(@ApiParam(required = true, name = "productId", value = "productId") @PathVariable String productId,
                                          @ApiParam(required = true, name = "id", value = "id") @PathVariable String id) {
        Product product = retrieveProduct(productId);
        ResponseEntity responseEntity = gatewayService.get(Services.PRICE_SERVICE, "/api/product-prices/" + id + "?productId=" + productId);
        ProductPrice productPrice = null;
        try {
            productPrice = objectMapper.readValue((String) responseEntity.getBody(), ProductPrice.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (product == null || productPrice == null || !product.getId().equals(productPrice.getProductId())) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return responseEntity;
        }
    }

    private Product retrieveProduct(String productId) {
        ResponseEntity responseEntity = gatewayService.get(Services.PRODUCT_SERVICE, "/api/products/" + productId);
        try {
            return objectMapper.readValue((String) responseEntity.getBody(), Product.class);
        } catch (IOException e) {
            return null;
        }
    }
}
