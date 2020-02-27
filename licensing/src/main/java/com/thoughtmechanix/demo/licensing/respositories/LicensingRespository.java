package com.thoughtmechanix.demo.licensing.respositories;

import com.thoughtmechanix.demo.licensing.models.License;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicensingRespository extends CrudRepository<License,String>{

    List<License> findByOrganizationId(String organizationId);

    License findByOrganizationIdAndLicenseId(String organizationId,String licenseId);
}
