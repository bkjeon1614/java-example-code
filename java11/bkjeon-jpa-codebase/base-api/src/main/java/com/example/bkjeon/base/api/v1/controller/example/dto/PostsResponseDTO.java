package com.example.bkjeon.base.api.v1.controller.example.dto;

import com.example.bkjeon.base.domain.posts.Posts;
import lombok.Getter;

@Getter
public class PostsResponseDTO {

    private Long id;
    private String title;
    private String content;
    private String author;

    public PostsResponseDTO(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }

}
