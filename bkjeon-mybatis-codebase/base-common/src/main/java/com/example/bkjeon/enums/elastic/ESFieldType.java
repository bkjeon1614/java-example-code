package com.example.bkjeon.enums.elastic;

import lombok.Getter;

@Getter
public enum ESFieldType {

    TEXT("text"),
    KEYWORD("keyword"),
    LONG("long"),
    DATE("date");

    private String type;

    ESFieldType(String type){
        this.type = type;
    }

}
