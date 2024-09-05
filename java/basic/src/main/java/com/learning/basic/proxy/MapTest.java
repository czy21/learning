package com.learning.basic.proxy;

import java.util.HashMap;
import java.util.Map;


public class MapTest {
    public static void main(String[] args) throws InterruptedException {
        new MapTest().func1();
        Thread.sleep(10 * 60000);
        System.out.println("");
    }

    public void func1() {
        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        map1.put("k1", "v1");
    }
}
