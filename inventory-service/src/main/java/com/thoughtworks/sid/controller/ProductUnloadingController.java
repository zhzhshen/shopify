package com.thoughtworks.sid.controller;

import com.thoughtworks.sid.domain.ProductLoading;
import com.thoughtworks.sid.domain.ProductUnloading;
import com.thoughtworks.sid.domain.Store;
import com.thoughtworks.sid.repository.ProductUnloadingRepository;
import com.thoughtworks.sid.repository.StoreRepository;
import com.thoughtworks.sid.service.InventoryService;
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
@RequestMapping(value = "/api/stores/{storeId}/products/{productId}/unloadings")
public class ProductUnloadingController {

    @Autowired
    ProductUnloadingRepository productUnloadingRepository;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    InventoryService inventoryService;

    @ApiOperation(value = "创建出库单")
    @RequestMapping(value = "/",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Store> createStore(@ApiParam(required = true, name = "storeId", value = "storeId") @PathVariable String storeId,
                                             @ApiParam(required = true, name = "productId", value = "productId") @PathVariable String productId,
                                             @RequestBody ProductUnloading productUnloading,
                                             UriComponentsBuilder uriBuilder,
                                             Principal principal) {
        if (principal == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        Store store = storeRepository.getByOwnerAndId(principal.getName(), Long.valueOf(storeId));
        if (store == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        productUnloading.setStoreId(Long.valueOf(storeId));
        productUnloading.setProductId(Long.valueOf(productId));
        ProductUnloading savedProductUnloading = productUnloadingRepository.save(productUnloading);
        inventoryService.unloading(savedProductUnloading);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriBuilder.path("/api/stores/{storeId}/products/{productId}/unloadings/{id}")
                .buildAndExpand(savedProductUnloading.getStoreId(), savedProductUnloading.getProductId(), savedProductUnloading.getId())
                .toUri());
        return new ResponseEntity(savedProductUnloading, headers, HttpStatus.CREATED);
    }

    @ApiOperation(value = "获取出库单列表")
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

        List<ProductUnloading> allUnloadings = productUnloadingRepository.getAllByStoreIdAndProductId(Long.valueOf(storeId), Long.valueOf(productId));
        return new ResponseEntity(allUnloadings, HttpStatus.OK);
    }

    @ApiOperation(value = "获取出库单信息")
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

        ProductUnloading productUnloading = productUnloadingRepository.getByIdAndStoreIdAndProductId(Long.valueOf(id), Long.valueOf(storeId), Long.valueOf(productId));
        return new ResponseEntity(productUnloading, productUnloading != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "确认出库单")
    @RequestMapping(value = "/{id}/confirmation", method = RequestMethod.POST)
    public ResponseEntity<Store> confirmUnloading(@ApiParam(required = true, name = "storeId", value = "storeId") @PathVariable String storeId,
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

        ProductUnloading productUnloading = productUnloadingRepository.getByIdAndStoreIdAndProductId(Long.valueOf(id), Long.valueOf(storeId), Long.valueOf(productId));
        productUnloading.confirm();
        ProductUnloading savedUnloading = productUnloadingRepository.save(productUnloading);
        return new ResponseEntity(savedUnloading, savedUnloading != null ? HttpStatus.CREATED : HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "取消出库单")
    @RequestMapping(value = "/{id}/cancellation", method = RequestMethod.POST)
    public ResponseEntity<Store> cancelUnloading(@ApiParam(required = true, name = "storeId", value = "storeId") @PathVariable String storeId,
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

        ProductUnloading productUnloading = productUnloadingRepository.getByIdAndStoreIdAndProductId(Long.valueOf(id), Long.valueOf(storeId), Long.valueOf(productId));
        productUnloading.cancel();
        ProductUnloading savedUnloading = productUnloadingRepository.save(productUnloading);
        inventoryService.cancelUnloading(savedUnloading);
        return new ResponseEntity(savedUnloading, savedUnloading != null ? HttpStatus.CREATED : HttpStatus.NOT_FOUND);
    }
}
