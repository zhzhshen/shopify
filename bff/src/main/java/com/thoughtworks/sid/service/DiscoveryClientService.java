package com.thoughtworks.sid.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class DiscoveryClientService {
    @Autowired
    private DiscoveryClient client;

    public ServiceInstance getServiceInstance(String serviceId) {
        List<ServiceInstance> instances = client.getInstances(serviceId);
        return instances.get(new Random().nextInt(instances.size()));
    }

    public String getServiceInstanceUrl(String serviceId) {
        return getServiceInstance(serviceId).getUri().toString();
    }
}
