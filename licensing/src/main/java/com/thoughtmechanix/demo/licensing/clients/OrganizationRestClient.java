package com.thoughtmechanix.demo.licensing.clients;

import com.thoughtmechanix.demo.licensing.models.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrganizationRestClient {

    @Autowired
    private RestTemplate restTemplate;

    public Organization getOrganization(String organizationId){
        ResponseEntity<Organization> exchange = restTemplate.exchange("http://organization/v1/organizations/{organizationId}", HttpMethod.GET, null, Organization.class, organizationId);
        return exchange.getBody();
    }
}
