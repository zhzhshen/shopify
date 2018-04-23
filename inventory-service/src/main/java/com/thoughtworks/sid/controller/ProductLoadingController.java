package com.thoughtworks.sid.controller;

import com.thoughtworks.sid.domain.ProductLoading;
import com.thoughtworks.sid.domain.Store;
import com.thoughtworks.sid.repository.ProductLoadingRepository;
import com.thoughtworks.sid.repository.StoreRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/api/stores/{storeId}/products/{productId}/loadings")
public class ProductLoadingController {

    @Autowired
    ProductLoadingRepository productLoadingRepository;

    @Autowired
    StoreRepository storeRepository;

    @ApiOperation(value = "创建入库单")
    @RequestMapping(value = "/",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Store> createStore(@ApiParam(required = true, name = "storeId", value = "storeId") @PathVariable String storeId,
                                             @ApiParam(required = true, name = "productId", value = "productId") @PathVariable String productId,
                                             @RequestBody ProductLoading productLoading,
                                             UriComponentsBuilder uriBuilder,
                                             Principal principal) {
        if (principal == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        Store store = storeRepository.getByOwnerAndId(principal.getName(), Long.valueOf(storeId));
        if (store == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        productLoading.setStoreId(Long.valueOf(storeId));
        productLoading.setProductId(Long.valueOf(productId));
        ProductLoading savedProductLoading = productLoadingRepository.save(productLoading);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriBuilder.path("/api/stores/{storeId}/products/{productId}/loadings/{id}")
                .buildAndExpand(savedProductLoading.getStoreId(), savedProductLoading.getProductId(), savedProductLoading.getId())
                .toUri());
        return new ResponseEntity(savedProductLoading, headers, HttpStatus.CREATED);
    }

    @ApiOperation(value = "获取入库单列表")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity getStore(@ApiParam(required = true, name = "storeId", value = "storeId") @PathVariable String storeId,
                                   @ApiParam(required = true, name = "productId", value = "productId") @PathVariable String productId,
                                   Principal principal) {
        if (principal == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        Store store = storeRepository.getByOwnerAndId(principal.getName(), Long.valueOf(storeId));
        if (store == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        List<ProductLoading> allInventories = productLoadingRepository.getAllByStoreIdAndProductId(Long.valueOf(storeId), Long.valueOf(productId));
        return new ResponseEntity(allInventories, HttpStatus.OK);
    }

    @ApiOperation(value = "获取入库单信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Store> getStore(@ApiParam(required = true, name = "storeId", value = "storeId") @PathVariable String storeId,
                                          @ApiParam(required = true, name = "productId", value = "productId") @PathVariable String productId,
                                          @ApiParam(required = true, name = "id", value = "id") @PathVariable String id,
                                          Principal principal) {
        if (principal == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        Store store = storeRepository.getByOwnerAndId(principal.getName(), Long.valueOf(storeId));
        if (store == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        ProductLoading productLoading = productLoadingRepository.getByIdAndStoreIdAndProductId(Long.valueOf(id), Long.valueOf(storeId), Long.valueOf(productId));
        return new ResponseEntity(productLoading, productLoading != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}
