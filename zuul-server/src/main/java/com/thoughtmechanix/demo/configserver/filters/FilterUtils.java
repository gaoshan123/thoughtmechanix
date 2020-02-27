package com.thoughtmechanix.demo.configserver.filters;

import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

@Component
public class FilterUtils {

    public static final String CORRELATION_ID = "tmx-correlation-id";
    public static final String AUTH_TOKEN     = "tmx-auth-token";
    public static final String USER_ID        = "tmx-user-id";
    public static final String ORG_ID         = "tmx-org-id";
    public static final String PRE_FILTER_TYPE = "pre";
    public static final String POST_FILTER_TYPE = "post";
    public static final String ROUTE_FILTER_TYPE = "route";

    public String getCorelationId(){
        RequestContext currentContext = RequestContext.getCurrentContext();
        if (currentContext.getRequest().getHeader(CORRELATION_ID) != null){
            return currentContext.getRequest().getHeader(CORRELATION_ID);
        }else{
            return currentContext.getZuulRequestHeaders().get(CORRELATION_ID);
        }
    }

    public void setCorrelationId(String correlationId){
        RequestContext currentContext = RequestContext.getCurrentContext();
        currentContext.addZuulRequestHeader(CORRELATION_ID,correlationId);
    }

    public String getOrgId(){
        RequestContext currentContext = RequestContext.getCurrentContext();
        if(currentContext.getRequest().getHeader(ORG_ID) != null){
            return currentContext.getRequest().getHeader(ORG_ID);
        }else{
            return currentContext.getZuulRequestHeaders().get(ORG_ID);
        }
    }

    public void setOrgId(String organiztionId){
        RequestContext currentContext = RequestContext.getCurrentContext();
        currentContext.addZuulRequestHeader(ORG_ID,organiztionId);
    }

    public String getUserId(){
        RequestContext currentContext = RequestContext.getCurrentContext();
        if (currentContext.getRequest().getHeader(USER_ID) != null){
            return currentContext.getRequest().getHeader(USER_ID);
        }else{
            return currentContext.getZuulRequestHeaders().get(USER_ID);
        }
    }

    public void setUserId(String userId){
        RequestContext currentContext = RequestContext.getCurrentContext();
        currentContext.addZuulRequestHeader(USER_ID,userId);
    }

    public String getAuthToken(){
        RequestContext currentContext = RequestContext.getCurrentContext();
        return currentContext.getRequest().getHeader(AUTH_TOKEN);
    }

    public String getServiceId(){
        RequestContext currentContext = RequestContext.getCurrentContext();
        if (currentContext.get("serviceId") == null){
            return "";
        }
        return currentContext.get("serviceId").toString();
    }
}
