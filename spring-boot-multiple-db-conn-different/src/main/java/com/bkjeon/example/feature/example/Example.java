package com.bkjeon.example.feature.example;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class Example {

    private Integer id;
    private String name;
    private Integer age;

    @Builder
    public Example(Integer id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

}
