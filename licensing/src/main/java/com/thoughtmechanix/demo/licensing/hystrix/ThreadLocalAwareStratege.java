package com.thoughtmechanix.demo.licensing.hystrix;

import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariable;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableLifecycle;
import com.netflix.hystrix.strategy.properties.HystrixProperty;
import com.thoughtmechanix.demo.licensing.util.UserContextHolder;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadLocalAwareStratege extends HystrixConcurrencyStrategy{

    private HystrixConcurrencyStrategy existingHystrixConcurrencyStrategy;

    public ThreadLocalAwareStratege(HystrixConcurrencyStrategy hystrixConcurrencyStrategy) {
        this.existingHystrixConcurrencyStrategy = hystrixConcurrencyStrategy;
    }

    @Override
    public ThreadPoolExecutor getThreadPool(HystrixThreadPoolKey threadPoolKey, HystrixProperty<Integer> corePoolSize, HystrixProperty<Integer> maximumPoolSize, HystrixProperty<Integer> keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        return existingHystrixConcurrencyStrategy != null?
                existingHystrixConcurrencyStrategy.getThreadPool(threadPoolKey,corePoolSize,maximumPoolSize,keepAliveTime,unit,workQueue):
                super.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    public BlockingQueue<Runnable> getBlockingQueue(int maxQueueSize) {
        return existingHystrixConcurrencyStrategy!=null?existingHystrixConcurrencyStrategy.getBlockingQueue(maxQueueSize):super.getBlockingQueue(maxQueueSize);
    }

    @Override
    public <T> Callable<T> wrapCallable(Callable<T> callable) {
        return existingHystrixConcurrencyStrategy != null?
                existingHystrixConcurrencyStrategy.wrapCallable(new DelegatingUserContextCallable<>(callable, UserContextHolder.getContext())):
                super.wrapCallable(new DelegatingUserContextCallable<>(callable,UserContextHolder.getContext()));
    }

    @Override
    public <T> HystrixRequestVariable<T> getRequestVariable(HystrixRequestVariableLifecycle<T> rv) {
        return existingHystrixConcurrencyStrategy!=null?existingHystrixConcurrencyStrategy.getRequestVariable(rv):super.getRequestVariable(rv);
    }
}
