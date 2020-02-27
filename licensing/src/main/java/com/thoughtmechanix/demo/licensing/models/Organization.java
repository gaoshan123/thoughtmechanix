package com.thoughtmechanix.demo.licensing.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
public class Organization {

    private String id;
    private String name;

    private String contactName;

    private String contactEmail;

    private String contactPhone;
}
