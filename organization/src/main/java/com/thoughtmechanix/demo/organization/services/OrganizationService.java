package com.thoughtmechanix.demo.organization.services;

import com.thoughtmechanix.demo.organization.models.Organizations;
import com.thoughtmechanix.demo.organization.respositoryies.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    public Organizations getOrg(String organizationId){
        return organizationRepository.findById(organizationId);
    }

    public void saveOrg(Organizations organizations){
        organizations.setId(UUID.randomUUID().toString());
        organizationRepository.save(organizations);
    }

    public void updateOrg(Organizations organizations){
        organizationRepository.save(organizations);
    }

    public void deleteOrg(Organizations organizations){
        organizationRepository.delete(organizations.getId());
    }
}
