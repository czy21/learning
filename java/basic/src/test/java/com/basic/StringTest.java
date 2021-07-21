package com.basic;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;


public class StringTest {

    /*
     * String 对象布局 及值的内存占用情况
     * .class底层使用unicode编码 英文字符占用1b 汉字占用2b
     */
    @Test
    public void test1() {
        String str1 = "y";
        System.out.println(StringUtils.join("ClassLayout info: ", ClassLayout.parseInstance(str1).toPrintable()));
        System.out.println(StringUtils.join("GraphLayout info: ", GraphLayout.parseInstance(str1).toPrintable()));
    }

    /*
     * String 定义的两种方式
     */
    @Test
    public void test2() {
        String str1 = "ni"; //存入常量池中
        String str2 = new String("ni"); //存入堆中
        System.out.println(str1 == str2);
    }
}
