package com.thoughtworks.sid.controller;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
public class HelloController {

    @RequestMapping("/")
    public String hello(Principal user) {
        return "hello " + user.getName();
    }

    @RequestMapping("/session")
    public Principal hello(HttpServletRequest request) {
        return request.getUserPrincipal();
    }
}
