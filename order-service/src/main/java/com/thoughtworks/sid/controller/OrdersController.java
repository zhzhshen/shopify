package com.thoughtworks.sid.controller;

import com.thoughtworks.sid.domain.Order;
import com.thoughtworks.sid.domain.OrderItem;
import com.thoughtworks.sid.repository.OrderRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/orders")
public class OrdersController {

    @Autowired
    OrderRepository orderRepository;

    @ApiOperation(value = "创建Order")
    @RequestMapping(value = "/",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> createOrder(@RequestBody String info,
                                             UriComponentsBuilder uriBuilder,
                                             Principal principal) throws IOException {
        if (principal == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        Order order = new ObjectMapper().readValue(info, Order.class);
        order.setCustomer(principal.getName());
        Order savedOrder = orderRepository.save(order);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriBuilder.path("/api/orders/{id}").buildAndExpand(savedOrder.getId()).toUri());
        return new ResponseEntity(savedOrder, headers, HttpStatus.CREATED);
    }

    @ApiOperation(value = "获取Order列表")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity getOrderList(Principal principal) {
        if (principal == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        List<Order> allOrders = orderRepository.getAllByCustomer(principal.getName());
        return new ResponseEntity(allOrders, HttpStatus.OK);
    }

    @ApiOperation(value = "获取Order信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Order> getOrder(@ApiParam(required = true, name = "id", value = "id") @PathVariable String id,
                                          Principal principal) {
        if (principal == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        Order order = orderRepository.getByCustomerAndId(principal.getName(), Long.valueOf(id));
        return new ResponseEntity(order, order != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "获取OrderItems列表")
    @RequestMapping(value = "/{id}/items", method = RequestMethod.GET)
    public ResponseEntity<List<OrderItem>> getOrderItems(@ApiParam(required = true, name = "id", value = "id") @PathVariable String id,
                                                         Principal principal) {
        if (principal == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        Order order = orderRepository.getByCustomerAndId(principal.getName(), Long.valueOf(id));
        List<OrderItem> orderItemList = order.getOrderItemList();
        return new ResponseEntity(orderItemList, orderItemList != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "获取OrderItems")
    @RequestMapping(value = "/{id}/items/{itemId}", method = RequestMethod.GET)
    public ResponseEntity<List<OrderItem>> getOrderItem(@ApiParam(required = true, name = "id", value = "id") @PathVariable String id,
                                                        @ApiParam(required = true, name = "itemId", value = "itemId") @PathVariable String itemId,
                                                         Principal principal) {
        if (principal == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        Order order = orderRepository.getByCustomerAndId(principal.getName(), Long.valueOf(id));
        List<OrderItem> orderItemList = order.getOrderItemList();
        Optional<OrderItem> orderItem = orderItemList.stream().filter(item -> itemId.equals(item.getId().toString())).findFirst();
        return new ResponseEntity(orderItem.orElseGet(null), orderItem.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}
