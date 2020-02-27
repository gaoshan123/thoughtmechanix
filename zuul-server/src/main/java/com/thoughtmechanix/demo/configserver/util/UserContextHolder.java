package com.thoughtmechanix.demo.configserver.util;

import java.util.Objects;

public class UserContextHolder {

    private final static ThreadLocal<UserContext> context = new ThreadLocal<>();

    public static UserContext getContext(){
        UserContext userContext = context.get();
        if (Objects.isNull(userContext)){
            userContext = new UserContext();
            context.set(userContext);
        }
        return context.get();
    }

    public static void setContext(UserContext userContext){
        context.set(userContext);
    }

}
