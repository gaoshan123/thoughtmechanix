package com.thoughtmechanix.demo.organization.controllers;

import com.thoughtmechanix.demo.organization.models.Organizations;
import com.thoughtmechanix.demo.organization.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @RequestMapping(value = "/{organizationId}",method = RequestMethod.GET)
    public Organizations getOrganizations(@PathVariable("organizationId")String organizationId){
        return organizationService.getOrg(organizationId);
    }

}
