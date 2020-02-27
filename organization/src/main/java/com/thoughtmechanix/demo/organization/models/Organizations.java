package com.thoughtmechanix.demo.organization.models;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Accessors(chain = true)
@Data
@Entity
@Table(name = "organizations")
public class Organizations {

    @Id
    @Column(name = "organization_id",nullable = false)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "contact_name",nullable = false)
    private String contactName;

    @Column(name = "contact_phone",nullable = false)
    private String contactPhone;

    @Column(name = "contact_email",nullable = false)
    private String contactEmail;
}
