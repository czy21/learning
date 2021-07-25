package com.basic;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import java.util.List;


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

    @Test
    public void test2() {
        Object1 o1 = new Object1();
        o1.setName("n");
        Object1 o2 = new Object1();
//        o2.setName("m");
        System.out.println(o1.hashCode());
        System.out.println(o2.hashCode());
    }

    @Test
    public void test3() {
        List<?> objs = List.of("Ea", "FB", "a", "a");
        for (int i = 0; i < objs.size(); i++) {
            int seq = i + 1;
            Object o = objs.get(i);
            System.out.println(StringUtils.join(List.of("o" + seq, "hashCode:", o.hashCode(), "address:", VM.current().addressOf(o)), " "));
        }
    }
}
