package com.example.bkjeon.base.actuator;

import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class MyStockManager implements Supplier<Number> {

    public long getStockCount() {
        return System.currentTimeMillis();
    }

    @Override
    public Number get() {
        return getStockCount();
    }

}
