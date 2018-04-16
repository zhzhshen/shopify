package com.thoughtworks.sid.gatewayService.controller;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class HelloController {

    @RequestMapping("/")
    public String hello(Principal user) {
        return "hello " + user.getName();
    }
}
