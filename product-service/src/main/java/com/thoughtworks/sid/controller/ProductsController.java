package com.thoughtworks.sid.controller;

import com.thoughtworks.sid.domain.Product;
import com.thoughtworks.sid.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/products")
public class ProductsController {
    @Value("${demo.env}")
    private String env;

    @RequestMapping("/hello")
    public String getProduct() {
        return "hello!";
    }

    @Autowired
    ProductRepository productRepository;

    @ApiOperation(value = "获取产品信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Product> getProduct(@ApiParam(required = true, name = "id", value = "id") @PathVariable String id,
                                            Principal user) {
        Product product = productRepository.findOne(Long.valueOf(id));
        return new ResponseEntity(product, product != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "创建产品")
    @RequestMapping(value = "/",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> createProduct(@RequestBody Map info,
                                               Principal user,
                                               UriComponentsBuilder uriBuilder) {
        Product savedImage = productRepository.save(new Product((String) info.get("name"), (String) info.get("description")));
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriBuilder.path("/api/products/{id}").buildAndExpand(savedImage.getId()).toUri());
        return new ResponseEntity(savedImage, headers, HttpStatus.CREATED);
    }
}
