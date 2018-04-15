package com.thoughtworks.sid.gatewayService.controller;

import com.thoughtworks.sid.gatewayService.domain.Product;
import com.thoughtworks.sid.gatewayService.repository.ProductRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.Principal;
import java.util.Map;

@RestController
public class HelloController {

    @RequestMapping("/")
    public String hello(Principal user) {
        return "hello " + user.getName();
    }
}
