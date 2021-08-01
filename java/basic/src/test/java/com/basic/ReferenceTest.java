package com.basic;

import org.junit.jupiter.api.Test;

public class ReferenceTest {
    class Object1{
        @Override
        protected void finalize() throws Throwable {
            System.out.println(this);
        }
    }
    @Test
    public void test1(){
        Object1 o1=new Object1();

    }
}
