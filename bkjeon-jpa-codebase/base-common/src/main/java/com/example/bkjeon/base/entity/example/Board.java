package com.example.bkjeon.base.entity.example;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardNo;

    private String title;
    private String contents;
    private String author;

    @Builder
    public Board(String title, String contents, String author) {
        this.title = title;
        this.contents = contents;
        this.author = author;
    }

}
