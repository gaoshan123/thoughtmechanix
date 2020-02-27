package com.thoughtmechanix.demo.organization.respositoryies;

import com.thoughtmechanix.demo.organization.models.Organizations;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends CrudRepository<Organizations,String> {

    Organizations findById(String organizationId);

}
