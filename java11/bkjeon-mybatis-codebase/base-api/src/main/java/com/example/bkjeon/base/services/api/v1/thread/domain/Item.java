package com.example.bkjeon.base.services.api.v1.thread.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Item {

    private String name;
    private Integer price;

    @Builder
    public Item(String name, Integer price) {
        this.name = name;
        this.price = price;
    }

}