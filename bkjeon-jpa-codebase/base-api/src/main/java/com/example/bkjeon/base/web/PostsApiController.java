package com.example.bkjeon.base.web;

import com.example.bkjeon.base.service.PostsService;
import com.example.bkjeon.base.web.dto.PostsResponseDTO;
import com.example.bkjeon.base.web.dto.PostsSaveRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @GetMapping("api/v1/posts/{id}")
    public PostsResponseDTO getPost(@PathVariable Long id) {
        return postsService.findById(id);
    }

    @PostMapping("api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDTO requestDTO) {
        return postsService.save(requestDTO);
    }

    @PutMapping("api/v1/posts/{id}")
    public PostsResponseDTO findById(@PathVariable Long id) {
        return postsService.findById(id);
    }


}
