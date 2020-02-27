package com.thoughtmechanix.demo.configserver.filters;

import com.netflix.discovery.converters.Auto;
import com.netflix.zuul.ZuulFilter;
import com.thoughtmechanix.demo.configserver.model.AbTestingRoute;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class SpecialRouteFilter extends ZuulFilter {

    private static final int FILTER_ORDER =  1;
    private static final boolean SHOULD_FILTER =true;

    @Autowired
    FilterUtils filterUtils;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String filterType() {
        return FilterUtils.ROUTE_FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    private ProxyRequestHelper helper = new ProxyRequestHelper();

    private AbTestingRoute getAbRoutingInfo(String serviceName){
        ResponseEntity<AbTestingRoute> restExchange = null;
        try {
            ResponseEntity<AbTestingRoute> exchange = restTemplate.exchange(
                    "http://specialroutesservice/v1/route/abtesting/{serviceName}",
                    HttpMethod.GET,
                    null, AbTestingRoute.class, serviceName
            );
            if (exchange.getStatusCode()== HttpStatus.OK){
                return exchange.getBody();
            }
            return null;

        }catch (HttpClientErrorException e){
            throw e;
        }
    }

    private String buildRouteString(String oldEndpoint,String newEndpoint,String serviceName){
        int index = oldEndpoint.indexOf(serviceName);
        String stripRoute = oldEndpoint.substring(index + serviceName.length());
        log.info("Target route:{}{}",newEndpoint,stripRoute);
        return String.format("%s%s",newEndpoint,stripRoute);
    }

    private String getVerb(HttpServletRequest request){
        String method = request.getMethod();
        return method.toUpperCase();
    }

//    private HttpHost
    // TODO: 2020/2/27  

    @Override
    public Object run() {
        return null;
    }
}
