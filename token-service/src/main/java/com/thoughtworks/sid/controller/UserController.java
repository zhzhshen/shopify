package com.thoughtworks.sid.controller;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
public class UserController {

    @RequestMapping("/")
    public String getToken(HttpServletRequest request) {
        return ((OAuth2AuthenticationDetails)((OAuth2Authentication)request.getUserPrincipal()).getDetails()).getTokenValue();
    }

    @RequestMapping({ "/user", "/me" })
    public Principal user(Principal principal, HttpServletRequest request) {
        return principal;
    }
}
