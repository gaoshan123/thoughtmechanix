package com.thoughtmechanix.demo.licensing.clients;

import com.thoughtmechanix.demo.licensing.models.Organization;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "organization")
public interface OrganizationFeignClient {

//    @RequestMapping注解使用value来声明URI并且URI不能为空
    @RequestMapping(value = "/v1/organizations/{organizationId}",method = RequestMethod.GET,consumes = "application/json")
    Organization getOrganization(@PathVariable("organizationId")String organizationId);
}
