package com.basic;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.ClassLayout;


public class ObjectTest {

    @Data
    static class Object1 {
        private final String str1 = "ni";
        private String str2;
        private int age;
        private String name;
        private Boolean died;
    }

    /*
     * jol 分析对象布局
     * markword 锁信息 00000101(偏向锁) 可通过 -XX:-UseBiasedLocking 关闭默认开启偏向锁
     */
    @Test
    public void test1() {
        Object1 o1 = new Object1();
        System.out.println(ClassLayout.parseInstance(o1).toPrintable());
    }
}
