package com.basic;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class GCTest {

    static class Object1 {

    }

    @Test
    public void test1() throws InterruptedException {
        List<Object1> object1s = new ArrayList<>();
        while (true) {
            Thread.sleep(1);
            object1s.add(new Object1());
        }
    }

}
