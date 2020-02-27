package com.thoughtmechanix.demo.configserver.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.plugin.util.UIUtil;

import java.util.UUID;

@Slf4j
@Component
public class TraceFilter extends ZuulFilter {

    private static final int      FILTER_ORDER =  1;
    private static final boolean  SHOULD_FILTER=true;

    @Autowired
    private FilterUtils filterUtils;


    @Override
    public String filterType() {
        return FilterUtils.PRE_FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    private boolean isCorrelationIdPresent(){
        if(filterUtils.getCorelationId() != null){
            return true;
        }
        return false;
    }

    private String generateCorrelationId(){
        return UUID.randomUUID().toString();
    }

    @Override
    public Object run() {

        if (isCorrelationIdPresent()){
            log.debug("tmx-correlation-id found in tracking filter: {}.",filterUtils.getCorelationId());
        }else{
            filterUtils.setCorrelationId(generateCorrelationId());
            log.debug("tmx-correlation-id found in tracking filter: {}.",filterUtils.getCorelationId());
        }

        RequestContext currentContext = RequestContext.getCurrentContext();
        log.debug("processing incoming request for {}",currentContext.getRequest().getRequestURI());

        return null;
    }
}
