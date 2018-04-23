package com.thoughtworks.sid.controller;

import com.thoughtworks.sid.domain.Order;
import com.thoughtworks.sid.domain.Payment;
import com.thoughtworks.sid.repository.OrderRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping(value = "/api/orders/{id}/payment")
public class PaymentsController {

    @Autowired
    OrderRepository orderRepository;

    @ApiOperation(value = "创建payment")
    @RequestMapping(value = "/",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> createOrder(@ApiParam(required = true, name = "id", value = "id") @PathVariable String id,
                                             @RequestBody Payment payment,
                                             UriComponentsBuilder uriBuilder,
                                             Principal principal) {
        if (principal == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        Order order = orderRepository.getByCustomerAndId(principal.getName(), Long.valueOf(id));

        if (order == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        order.setPayment(payment);
        Order savedOrder = orderRepository.save(order);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriBuilder.path("/api/orders/{id}/payments/").buildAndExpand(savedOrder.getId()).toUri());
        return new ResponseEntity(savedOrder, headers, HttpStatus.CREATED);
    }

    @ApiOperation(value = "获取payment")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity getOrderList(@ApiParam(required = true, name = "id", value = "id") @PathVariable String id,
                                       Principal principal) {
        if (principal == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        Order order = orderRepository.getByCustomerAndId(principal.getName(), Long.valueOf(id));

        if (order == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        Payment payment = order.getPayment();
        return new ResponseEntity(payment, payment == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }
}
