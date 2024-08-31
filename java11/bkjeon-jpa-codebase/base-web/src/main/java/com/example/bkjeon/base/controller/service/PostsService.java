package com.example.bkjeon.base.controller.service;

import com.example.bkjeon.base.domain.posts.Posts;
import com.example.bkjeon.base.repository.posts.PostsRepository;
import com.example.bkjeon.base.controller.posts.dto.PostsListResponseDTO;
import com.example.bkjeon.base.controller.posts.dto.PostsResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    public List<PostsListResponseDTO> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDTO::new)
                .collect(Collectors.toList());
    }

    public PostsResponseDTO findById(Long id) {
        Posts entity = postsRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDTO(entity);
    }

}
