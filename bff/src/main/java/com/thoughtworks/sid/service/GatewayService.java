package com.thoughtworks.sid.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.ribbon.proxy.annotation.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.*;

@Service
public class GatewayService {

    @Autowired
    private DiscoveryClientService client;

    private String getServiceInstanceUrl(String serviceId) {
        return client.getServiceInstanceUrl(serviceId);
    }

    public ResponseEntity post(String serviceId, String url, Object object) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");

        HttpEntity<String> httpEntity = null;
        try {
            httpEntity = new HttpEntity<String>(new ObjectMapper().writeValueAsString(object), httpHeaders);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(getServiceInstanceUrl(serviceId) + url, HttpMethod.POST, httpEntity, String.class);
    }

    public ResponseEntity get(HttpServletRequest request, String serviceId, String url) {
        return get(request, serviceId, url, Collections.emptyMap());
    }

    public ResponseEntity get(HttpServletRequest request, String serviceId, String url, Map<String, String> params) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(getServiceInstanceUrl(serviceId) + formatUrl(request, url, params),
                HttpMethod.GET,
                null,
                String.class);
    }

    private String formatUrl(HttpServletRequest request, String url, Map<String, String> params) {
        String token = retrieveToken(request);
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(url).queryParam("access_token", token);
        params.keySet().stream().forEach(key -> builder.queryParam(key, params.get(key)));
        return builder.toUriString();
    }

    private String retrieveToken(HttpServletRequest request) {
        Authentication auth = (Authentication) request.getUserPrincipal();
        if (auth instanceof OAuth2Authentication) {
            Object details = auth.getDetails();
            if (details instanceof OAuth2AuthenticationDetails) {
                OAuth2AuthenticationDetails oauth = (OAuth2AuthenticationDetails) details;
                return oauth.getTokenValue();
            }
        }
        return null;
    }
}
