package com.thoughtworks.sid.controller;

import com.thoughtworks.sid.constant.Services;
import com.thoughtworks.sid.domain.Product;
import com.thoughtworks.sid.service.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/products")
public class ProductsController {

    @Autowired
    private GatewayService gatewayService;

    @ApiOperation(value = "创建产品")
    @RequestMapping(value = "/",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createProduct(@RequestBody Product product) {
        return gatewayService.post(Services.PRODUCT_SERVICE, "/api/products/", product);
    }

    @ApiOperation(value = "获取产品列表")
    @RequestMapping(value = "/",
            method = RequestMethod.GET)
    public ResponseEntity getProductsList() {
        return gatewayService.get(Services.PRODUCT_SERVICE, "/api/products/");
    }
}
