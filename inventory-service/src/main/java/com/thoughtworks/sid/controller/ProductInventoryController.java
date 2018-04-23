package com.thoughtworks.sid.controller;

import com.thoughtworks.sid.domain.Inventory;
import com.thoughtworks.sid.domain.Store;
import com.thoughtworks.sid.repository.InventoryRepository;
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
@RequestMapping(value = "/api/stores/{storeId}/products/{productId}")
public class ProductInventoryController {

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    StoreRepository storeRepository;

    @ApiOperation(value = "创建库存单")
    @RequestMapping(value = "/inventories",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Store> createStore(@ApiParam(required = true, name = "storeId", value = "storeId") @PathVariable String storeId,
                                             @ApiParam(required = true, name = "productId", value = "productId") @PathVariable String productId,
                                             @RequestBody Inventory inventory,
                                             UriComponentsBuilder uriBuilder,
                                             Principal principal) {
        if (principal == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        Store store = storeRepository.getByOwnerAndId(principal.getName(), Long.valueOf(storeId));
        if (store == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        inventory.setStoreId(Long.valueOf(storeId));
        inventory.setProductId(Long.valueOf(productId));
        Inventory savedInventory = inventoryRepository.save(inventory);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriBuilder.path("/api/stores/{storeId}/products/{productId}/inventories/{id}")
                .buildAndExpand(savedInventory.getStoreId(), savedInventory.getProductId(), savedInventory.getId())
                .toUri());
        return new ResponseEntity(savedInventory, headers, HttpStatus.CREATED);
    }

    @ApiOperation(value = "获取库存单列表")
    @RequestMapping(value = "/inventories", method = RequestMethod.GET)
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

        List<Inventory> allInventories = inventoryRepository.getAllByStoreIdAndProductId(Long.valueOf(storeId), Long.valueOf(productId));
        return new ResponseEntity(allInventories, HttpStatus.OK);
    }

    @ApiOperation(value = "获取库存信息")
    @RequestMapping(value = "/inventories/{id}", method = RequestMethod.GET)
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

        Inventory inventory = inventoryRepository.getByIdAndStoreIdAndProductId(Long.valueOf(id), Long.valueOf(storeId), Long.valueOf(productId));
        return new ResponseEntity(inventory, inventory != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}
