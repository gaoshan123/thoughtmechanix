package com.thoughtmechanix.demo.licensing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
/**
 * 向eureka进行注册这个注解不能少
 */
@EnableEurekaClient
@RefreshScope
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
/**
 * spring boot处理filter servlet以及listener的两种方式之一，使用注解的这三种组件，可以使用这个注解让spring扫描识别纳入管理，
 * 不是通过注解的形式,要，在@configuration中进行配置，例如 @Bean public FilterRegisterBean corsFilter(){}
 */
@ServletComponentScan
public class LicensingApplication {

	public static void main(String[] args) {
		SpringApplication.run(LicensingApplication.class, args);
	}

	@LoadBalanced
	@Bean
	RestTemplate restTemplate(){
		return new RestTemplate();
	}

}
