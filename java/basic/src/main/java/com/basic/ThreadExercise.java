package com.basic;

import lombok.Data;
import org.openjdk.jol.info.ClassLayout;

public class ThreadExercise {


    @Data
    public static class Object1 {
        private String name;
        private Integer age;
        private int value;
        public void plus() {
            int i = 3;
            int j = 9;
            value=i+j;
        }
    }

    public static void main(String[] args) {

//        AtomicInteger atomicInteger = new AtomicInteger();

//        Map<String, Object> m1 = new HashMap<>();
//        m1.put("name", 1);
        Object1 o1 = new Object1();
        o1.plus();
        o1.setName("你是谁");
        System.out.println(ClassLayout.parseInstance(o1.getValue()).toPrintable());

    }
}
