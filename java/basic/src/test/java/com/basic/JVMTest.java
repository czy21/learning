package com.basic;

import org.junit.jupiter.api.Test;
import org.openjdk.jol.vm.VM;

public class JVMTest {

    @Test
    public void test1(){
        System.out.println(VM.current().details());
    }
}
