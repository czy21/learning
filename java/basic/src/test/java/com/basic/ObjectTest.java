package com.basic;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.util.RamUsageEstimator;
import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;


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
        System.out.println(ClassLayout.parseInstance(o1).toPrintable());
    }

    /*
     * String 对象布局
     */
    @Test
    public void test2() {
        String str1 = "n";
        System.out.println(StringUtils.join("ClassLayout info: ", ClassLayout.parseInstance(str1).toPrintable()));
        System.out.println(StringUtils.join("GraphLayout info: ", GraphLayout.parseInstance(str1).toPrintable()));
        System.out.println(StringUtils.join("GraphLayout size: ", GraphLayout.parseInstance(str1).toPrintable()));
        System.out.println(StringUtils.join("RamUsageEstimator size: ", RamUsageEstimator.sizeOf(str1)));
    }
}
