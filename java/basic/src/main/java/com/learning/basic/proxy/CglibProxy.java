package com.learning.basic.proxy;


import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;


public class CglibProxy implements MethodInterceptor {

    private static CglibProxy instance = new CglibProxy();

    private CglibProxy() {
    }

    public static CglibProxy getInstance() {
        return instance;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> cls) {
        return (T) Enhancer.create(cls, this);
    }


    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

        System.out.println("before");
        Object val = proxy.invokeSuper(obj, args);
        System.out.println("after");
        return val;
    }
}