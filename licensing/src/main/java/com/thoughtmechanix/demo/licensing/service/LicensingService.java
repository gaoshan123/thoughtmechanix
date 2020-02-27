package com.thoughtmechanix.demo.licensing.service;

import ch.qos.logback.core.util.TimeUtil;
import com.google.common.collect.Lists;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.thoughtmechanix.demo.licensing.ClientTypesEnum;
import com.thoughtmechanix.demo.licensing.clients.OrganizationDiscoveryClient;
import com.thoughtmechanix.demo.licensing.clients.OrganizationFeignClient;
import com.thoughtmechanix.demo.licensing.clients.OrganizationRestClient;
import com.thoughtmechanix.demo.licensing.config.ServiceConfig;
import com.thoughtmechanix.demo.licensing.models.License;
import com.thoughtmechanix.demo.licensing.models.Organization;
import com.thoughtmechanix.demo.licensing.respositories.LicensingRespository;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.LICENSE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class LicensingService {

    @Autowired
    private LicensingRespository licensingRespository;

    @Autowired
    private ServiceConfig serviceConfig;

    @Autowired
    private OrganizationDiscoveryClient organizationDiscoveryClient;

    @Autowired
    private OrganizationFeignClient organizationFeignClient;

    @Autowired
    private OrganizationRestClient organizationRestClient;

    public License getLicense(String organizationId,String licenseId) {
        License license = licensingRespository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        license.setComment(serviceConfig.getTracerProperty());
        return license;
    }

    private void randomRunLong(){
        Random random = new Random();
        int i = random.nextInt((3 - 1) + 1) + 1;
        log.info("随机数:{}",i);
        if(i==3){
            try {
                TimeUnit.SECONDS.sleep(11);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

//    HystrixCommandProperties
//    HystixCommandProperties
    @HystrixCommand(
            fallbackMethod = "buildFallbackLicenseList",
            threadPoolKey = "licensesByOrgThreadPool",
            threadPoolProperties={
                    @HystrixProperty(name="coreSize",value = "30"),
                    @HystrixProperty(name="maxQueueSize",value = "10")
            },
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "75"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "7000"),
                    @HystrixProperty(name="metrics.rollingStats.timeInMilliseconds",value = "15000"),
                    @HystrixProperty(name="metrics.rollingStats.numBuckets",value = "5"),
            }
    )
    public List<License> getLicensesByOrg(String organizationId){
        return licensingRespository.findByOrganizationId( organizationId );
    }
//
//    public Organization getOrganization(String organizationId){
//
//    }

    @HystrixCommand
    public License getLicense(String organizationId,String licenseId,String clientType){
        License license = licensingRespository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        Organization organization = retriveOrgInfo(organizationId, clientType);
        if (Objects.nonNull(organization)){
            return license
                    .setLicenseType(organization.getName())
                    .setOrganizationId(organization.getId())
                    .setContactEmail(organization.getContactEmail())
                    .setContactName(organization.getContactName())
                    .setContactPhone(organization.getContactPhone())
                    .setComment("客户端:"+clientType)
                    .setTestField(serviceConfig.getTracerProperty());
        }
        return null;
    }

    private Organization retriveOrgInfo(String organizationId, String type) {

        Organization organization = null;
        Optional<ClientTypesEnum> clientType = ClientTypesEnum.getByType(type);
        if (clientType.isPresent()){
            ClientTypesEnum clientTypesEnum = clientType.get();
            switch (clientTypesEnum) {
                case FEIGN:
                    log.info("I am using the feign client");
                    organization = organizationFeignClient.getOrganization(organizationId);
                    break;
                case REST:
                    log.info("I am using the rest client");
                    organization = organizationRestClient.getOrganization(organizationId);
                    break;
                case DISCOVERY:
                    log.info("I am using the discovery client");
                    organization = organizationDiscoveryClient.getOrganization(organizationId);
                    break;
                default:
                    log.info("I am using the default rest client......");
                    organization = organizationRestClient.getOrganization(organizationId);
            }
        }



        return organization;

    }

    public List<License> buildFallbackLicenseList(String organizationId){

        return Lists.newArrayList(
                new License()
                .setOrganizationId(organizationId)
                .setLicenseId("00000-0000")
                .setProductName("Sorry no licensing information currently available")
        );
    }

    public void saveLicense(License license){
        license.setLicenseId(UUID.randomUUID().toString());;
        licensingRespository.save(license);
    }

    public void updateLicense(License license){
        licensingRespository.save(license);
    }

    public void deleteLicense(License license){
        licensingRespository.delete(license);
    }
}
