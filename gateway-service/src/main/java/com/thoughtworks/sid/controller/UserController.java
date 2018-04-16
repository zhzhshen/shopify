package com.thoughtworks.sid.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
public class UserController {

    @RequestMapping("/hello")
    public String getProduct() {
        return "hello!";
    }

    @RequestMapping("/session")
    public Principal hello(HttpServletRequest request) {
        return request.getUserPrincipal();
    }

    @RequestMapping({ "/user", "/me" })
    public Principal user(Principal principal) {
        return principal;
    }
}
