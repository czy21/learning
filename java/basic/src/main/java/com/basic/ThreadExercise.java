package com.basic;

import com.clearning.entity.po.UserPO;
import lombok.Data;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

public class ThreadExercise {


    @Data
    public static class Object1 {
        private String name;
        private Integer age;
        private int value;

        public void plus() {
            int i = 3;
            int j = 9;
            value = i + j;
        }
    }

    public static void main(String[] args) {

//        AtomicInteger atomicInteger = new AtomicInteger();

//        Map<String, Object> m1 = new HashMap<>();
//        m1.put("name", 1);
        Object o1 = new Object();
//        o1.plus();
//        o1.setName("你是谁");
        System.out.println(ClassLayout.parseInstance(o1).toPrintable());

        synchronized (o1) {
            System.out.println(ClassLayout.parseInstance(o1).toPrintable());
        }


//        UserPO u1=new UserPO();
//
//        System.out.println(ClassLayout.parseInstance(u1).toPrintable());
//
//        synchronized (u1){
//            System.out.println(ClassLayout.parseInstance(u1).toPrintable());
//        }
    }
}
