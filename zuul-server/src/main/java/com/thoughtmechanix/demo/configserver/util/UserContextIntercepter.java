package com.thoughtmechanix.demo.configserver.util;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * spring mvc的叫HandlerInterceptor,这里的ClientHttpRequestInterceptor用于restTemplate
 */
public class UserContextIntercepter implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();
        headers.add(UserContext.AUTH_TOKEN,UserContextHolder.getContext().getAuthToken());
        headers.add(UserContext.CORRELATION_ID,UserContextHolder.getContext().getCorrelationId());
        return execution.execute(request,body);
    }
}
