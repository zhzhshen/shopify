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
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
                                             @RequestBody ProductPrice productPrice,
                                             HttpServletRequest request) {
        Product product = retrieveProduct(request, productId);

        if (product == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            productPrice.setProductId(Long.valueOf(productId));
            return gatewayService.post(request, Services.PRICE_SERVICE, "/api/product-prices/", productPrice);
        }
    }

    @ApiOperation(value = "获取产品价格列表")
    @RequestMapping(value = "/",
            method = RequestMethod.GET)
    public ResponseEntity getProductPricesList(@ApiParam(required = true, name = "productId", value = "productId") @PathVariable String productId,
                                               HttpServletRequest request) {
        Product product = retrieveProduct(request, productId);

        if (product == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            Map<String, String> params = new HashMap<>();
            params.put("productId", productId);
            return gatewayService.get(request, Services.PRICE_SERVICE, "/api/product-prices/", params);
        }
    }

    @ApiOperation(value = "获取当前产品价格")
    @RequestMapping(value = "/latest", method = RequestMethod.GET)
    public ResponseEntity getCurrentProductPrice(@ApiParam(required = true, name = "productId", value = "productId") @PathVariable String productId,
                                                 HttpServletRequest request) {
        Product product = retrieveProduct(request, productId);
        Map<String, String> params = new HashMap<>();
        params.put("productId", productId);
        ResponseEntity responseEntity = gatewayService.get(request, Services.PRICE_SERVICE, "/api/product-prices/latest", params);
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

    @ApiOperation(value = "获取产品价格")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getProductPrice(@ApiParam(required = true, name = "productId", value = "productId") @PathVariable String productId,
                                          @ApiParam(required = true, name = "id", value = "id") @PathVariable String id,
                                          HttpServletRequest request) {
        Product product = retrieveProduct(request, productId);
        Map<String, String> params = new HashMap<>();
        params.put("productId", productId);
        ResponseEntity responseEntity = gatewayService.get(request, Services.PRICE_SERVICE, "/api/product-prices/" + id, params);
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

    private Product retrieveProduct(HttpServletRequest request, String productId) {
        ResponseEntity responseEntity = gatewayService.get(request, Services.PRODUCT_SERVICE, "/api/products/" + productId);
        try {
            return objectMapper.readValue((String) responseEntity.getBody(), Product.class);
        } catch (IOException e) {
            return null;
        }
    }
}
