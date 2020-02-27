package com.thoughtmechanix.demo.licensing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiscoveryService {

    @Autowired
    private DiscoveryClient discoveryClient;

    public List getEurekaServices(){
        List<String> services = new ArrayList<>();
        discoveryClient.getServices().forEach(service -> {
            List<ServiceInstance> instances = discoveryClient.getInstances(service);
            instances.forEach(instance -> services.add(String.format("%s:%s",service,instance.getUri())));
        });
        return services;
    }
}
