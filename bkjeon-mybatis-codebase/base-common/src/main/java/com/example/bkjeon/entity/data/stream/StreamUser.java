package com.example.bkjeon.entity.data.stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class StreamUser {

    private String name;
    private int age;

    public StreamUser(StreamUser streamUser) {
        this.name = streamUser.name;
        this.age = streamUser.age;
    }

}
