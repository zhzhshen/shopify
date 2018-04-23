package com.thoughtworks.sid.controller;

import com.thoughtworks.sid.domain.Store;
import com.thoughtworks.sid.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

@RestController
@RequestMapping(value = "/api/stores")
public class StoresController {

    @Autowired
    StoreRepository storeRepository;

    @ApiOperation(value = "创建Store")
    @RequestMapping(value = "/",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Store> createStore(@RequestBody Store store,
                                               UriComponentsBuilder uriBuilder,
                                               Principal principal) {
        if (principal == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        store.setOwner(principal.getName());
        Store savedStore = storeRepository.save(store);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriBuilder.path("/api/stores/{id}").buildAndExpand(savedStore.getId()).toUri());
        return new ResponseEntity(savedStore, headers, HttpStatus.CREATED);
    }

    @ApiOperation(value = "获取Store列表")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity getStore(Principal principal) {
        if (principal == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        List<Store> allStores = storeRepository.getAllByOwner(principal.getName());
        return new ResponseEntity(allStores, HttpStatus.OK);
    }

    @ApiOperation(value = "获取Store信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Store> getStore(@ApiParam(required = true, name = "id", value = "id") @PathVariable String id,
                                            Principal principal) {
        if (principal == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        Store store = storeRepository.getByOwnerAndId(principal.getName(), Long.valueOf(id));
        return new ResponseEntity(store, store != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}
