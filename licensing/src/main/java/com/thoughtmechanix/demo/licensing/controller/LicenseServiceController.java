package com.thoughtmechanix.demo.licensing.controller;

import com.thoughtmechanix.demo.licensing.ClientTypesEnum;
import com.thoughtmechanix.demo.licensing.models.License;
import com.thoughtmechanix.demo.licensing.service.LicensingService;
import com.thoughtmechanix.demo.licensing.service.OrganizationService;
import org.bouncycastle.LICENSE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/organizations/{organizationId}/licenses")
public class LicenseServiceController {


    @Autowired
    private LicensingService licensingService;

    @Autowired
    private OrganizationService organizationService;

    @RequestMapping("/{licenseId}")
    public License getLicenses(@PathVariable("organizationId") String organizationId,
                               @PathVariable("licenseId")String licenseId){


        return new License().setLicenseId(licenseId)
                .setLicenseType("seat")
                .setProductName("Teleco")
                .setOrganizationId("TestOrg");
    }

    @RequestMapping("/{licenseId}/{clientType}")
    public License getLicenseswithClient(@PathVariable("licenseId") String licenseId,
                                         @PathVariable("organizationId")String organizationId,
                                         @PathVariable("clientType")String clientType){


        return licensingService.getLicense(organizationId,licenseId,clientType);
    }
}
