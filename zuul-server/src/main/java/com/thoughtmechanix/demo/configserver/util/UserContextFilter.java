package com.thoughtmechanix.demo.configserver.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@WebFilter(filterName = "userContextFilter")
/**
 * 在spring boot中通过这个指定顺序，默认容器中是按照filtename的字母排序执行的，xml形式是按照声明定义的顺序执行的
 * 在spring中如果通过registerbean形式进行在spring中管理，对应的registerbean可以设置执行的顺序
 */
/**
 * 经测试，网上说通过@order调节注解的filter的执行顺序是无效，看来只能通过registerBean的形式了
 */
@Order(1)
public class UserContextFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("初始化 filter1");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest res = (HttpServletRequest) request;
        String authToken = res.getHeader(UserContext.AUTH_TOKEN);
        String correlationId = res.getHeader(UserContext.CORRELATION_ID);
        String userId = res.getHeader(UserContext.USER_ID);
        String orgId = res.getHeader(UserContext.ORG_ID);
        UserContextHolder.getContext().setAuthToken(authToken);
        UserContextHolder.getContext().setCorrelationId(correlationId);
        UserContextHolder.getContext().setOrgId(orgId);
        UserContextHolder.getContext().setUserId(userId);
    }

    @Override
    public void destroy() {

    }
}
