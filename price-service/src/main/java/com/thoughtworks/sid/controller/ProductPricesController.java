package com.thoughtworks.sid.controller;

import com.thoughtworks.sid.domain.ProductPrice;
import com.thoughtworks.sid.repository.ProductPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/product-prices")
public class ProductPricesController {

    @Autowired
    ProductPriceRepository productPriceRepository;

    @ApiOperation(value = "创建产品价格")
    @RequestMapping(value = "/",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductPrice> createProductPrice(@RequestBody ProductPrice productPrice,
                                                           UriComponentsBuilder uriBuilder) {
        ProductPrice savedProductPrice = productPriceRepository.save(productPrice);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriBuilder.path("/api/product-prices/{id}").buildAndExpand(savedProductPrice.getId()).toUri());
        return new ResponseEntity(savedProductPrice, headers, HttpStatus.CREATED);
    }

    @ApiOperation(value = "获取产品价格列表")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity getProductPrices(@ApiParam(name = "productId", value = "productId") @RequestParam(value = "productId") Optional<String> productId) {
        List<ProductPrice> allProductPrices = productId.isPresent() ? productPriceRepository.findProductPricesByProductId(Long.valueOf(productId.get())) : productPriceRepository.findAll();
        return new ResponseEntity(allProductPrices, HttpStatus.OK);
    }

    @ApiOperation(value = "获取产品价格信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ProductPrice> getProductPrice(@ApiParam(required = true, name = "id", value = "id") @PathVariable String id) {
        ProductPrice productPrice = productPriceRepository.findOne(Long.valueOf(id));
        return new ResponseEntity(productPrice, productPrice != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}
