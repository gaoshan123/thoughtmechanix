package com.thoughtmechanix.demo.licensing.hystrix;

import com.thoughtmechanix.demo.licensing.util.UserContext;
import com.thoughtmechanix.demo.licensing.util.UserContextHolder;

import java.util.concurrent.Callable;

public class DelegatingUserContextCallable<V> implements Callable {

    private final Callable<V> delegate;

    private UserContext originalUserContext;

    public DelegatingUserContextCallable(Callable<V> delegate,UserContext userContext) {
        this.delegate = delegate;
        this.originalUserContext = userContext;
    }

    @Override
    public Object call() throws Exception {
        UserContextHolder.setContext(this.originalUserContext);
        try {
            return delegate.call();
        }finally {
            this.originalUserContext = null;
        }
    }

    public static <V> Callable<V> create(Callable<V> delegate,UserContext userContext){
        return new DelegatingUserContextCallable<V>(delegate,userContext);
    }
}
