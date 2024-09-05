package com.basic;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
     * java.lang.Object object internals:
     *  OFFSET  SIZE   TYPE DESCRIPTION
     *       0     4        (object header)                            |  markword
     *       4     4        (object header)                            |  markword
     *       8     4        (object header)                            |  classPointer
     *      12     4        (loss due to the next object alignment)    |  padding 对齐
     * classPointer 压缩: 4b(padding对齐),未压缩: 8b(无需padding对齐)
     * Instance size: 16 bytes
     */
    @Test
    public void test1() {
        Object o1 = new Object();
        Object1 o2 = new Object1();
        System.out.println("o1: " + ClassLayout.parseInstance(o1).toPrintable());
        System.out.println("o2: " + ClassLayout.parseInstance(o2).toPrintable());
    }

    /*
     * 锁信息在markword
     * 默认为: 01010011
     * 偏向锁: 00000101 参数: -XX:-UseBiasedLocking
     * 锁升级状态: new -> 偏向锁 -> 轻量级锁(无锁|自旋锁|自适应锁) -> 重量级锁
     * 锁状态     1bit(偏向锁位)     2bit(锁标志)
     *  new      0                 01
     *  偏向锁    1                 01
     *  自旋锁                      00
     *  重量级                      10
     *  GC标记                      11
     */
    @Test
    public void test11() {
        Object o1 = new Object();
        System.out.println("original: " + ClassLayout.parseInstance(o1).toPrintable());
        synchronized (o1) {
            System.out.println("synchronized: " + ClassLayout.parseInstance(o1).toPrintable());
        }
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

    @Test
    public void test4() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Object o1 = Object1.class.getDeclaredConstructor().newInstance();
        System.out.println("1");
    }
}
