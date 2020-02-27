package com.thoughtmechanix.demo.licensing.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class ServiceConfig {

    @Value("${tracer.property}")
    private String tracerProperty;


}
