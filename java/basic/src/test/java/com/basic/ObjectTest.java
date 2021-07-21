package com.basic;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;
import org.openjdk.jol.vm.VM;

import java.nio.charset.StandardCharsets;


public class ObjectTest {

    @Data
    static class Object1 {
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
        o1.setName("你");
        String str1 = "o";
        System.out.println(ClassLayout.parseInstance(str1).toPrintable());
        System.out.println(GraphLayout.parseInstance(str1).toPrintable());
    }
}
