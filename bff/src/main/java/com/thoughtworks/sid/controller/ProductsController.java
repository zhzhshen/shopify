package com.thoughtworks.sid.controller;

import com.netflix.ribbon.proxy.annotation.Http;
import com.thoughtworks.sid.constant.Services;
import com.thoughtworks.sid.domain.Product;
import com.thoughtworks.sid.service.GatewayService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

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
    public ResponseEntity getProductsList(HttpServletRequest request) {
        return gatewayService.get(request, Services.PRODUCT_SERVICE, "/api/products/");
    }

    @ApiOperation(value = "获取产品信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getProduct(@ApiParam(required = true, name = "id", value = "id") @PathVariable String id,
                                     HttpServletRequest request) {
        return gatewayService.get(request, Services.PRODUCT_SERVICE, "/api/products/" + id);
    }
}
