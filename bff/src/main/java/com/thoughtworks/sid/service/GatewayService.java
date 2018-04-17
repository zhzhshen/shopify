package com.thoughtworks.sid.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

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

    public ResponseEntity get(String serviceId, String url) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(getServiceInstanceUrl(serviceId) + url, HttpMethod.GET, null, String.class);
    }
}
