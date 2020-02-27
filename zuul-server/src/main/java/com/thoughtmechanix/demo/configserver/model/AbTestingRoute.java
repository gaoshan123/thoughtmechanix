package com.thoughtmechanix.demo.configserver.model;

import lombok.Data;

@Data
public class AbTestingRoute {

    String serviceName;

    String active;

    String endpoint;

    Integer weight;
}
