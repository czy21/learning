package com.basic;

import com.basic.proxy.Car;
import com.basic.proxy.CarImpl;
import com.basic.proxy.CglibProxy;
import com.basic.proxy.JdkProxy;

public class ProxyTest {


    public static void main(String[] args) {
        JdkProxy dynamicProxy = new JdkProxy(new CarImpl());
        Car car = dynamicProxy.getTarget();
        String ret1 = car.buy("jjj");
        System.out.println(ret1);

        Car car1 = CglibProxy.getInstance().getProxy(CarImpl.class);
        String ret2 = car1.buy("a");
        System.out.println(ret2);
    }
}
