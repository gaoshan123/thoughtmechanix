package com.thoughtmechanix.demo.licensing.models;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;


@Accessors(chain = true)
@Data
@Entity
@Table(name = "licenses")
public class License {

    @Id
    @Column(name = "license_id",nullable = false)
    private String licenseId;

    @Column(name = "organization_id",nullable = false)
    private String organizationId;

    @Column(name = "product_name",nullable = false)
    private String productName;

    @Column(name = "license_type")
    private String licenseType;

    @Column(name = "license_max")
    private Integer licenseMax;

    @Column(name = "license_allocated")
    private Integer licenseAllocated;

    @Column(name = "comment")
    private String comment;

    @Transient
    private String organizationName ="";

    @Transient
    private String contactName ="";

    @Transient
    private String contactPhone ="";

    @Transient
    private String contactEmail ="";

    @Transient
    private String testField;
}
