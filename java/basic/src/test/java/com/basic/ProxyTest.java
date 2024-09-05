package com.basic;

import com.learning.basic.proxy.Car;
import com.learning.basic.proxy.CarImpl;
import com.learning.basic.proxy.CglibProxy;
import com.learning.basic.proxy.JdkProxy;
import org.junit.jupiter.api.Test;

public class ProxyTest {

    /*
     * jdk动态代理
     */
    @Test
    public void test1() {
        JdkProxy dynamicProxy = new JdkProxy(new CarImpl());
        Car car = dynamicProxy.getTarget();
        String ret1 = car.buy("a");
        System.out.println(ret1);
    }

    /*
     * cglib动态代理
     */
    @Test
    public void test2() {
        Car car1 = CglibProxy.getInstance().getProxy(CarImpl.class);
        String ret2 = car1.buy("a");
        System.out.println(ret2);
    }
}
