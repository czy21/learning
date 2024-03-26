package com.learning.basic.proxy;

public class CarImpl implements Car {
    @Override
    public String buy(String who) {
        return who + "买啥";
    }
}
