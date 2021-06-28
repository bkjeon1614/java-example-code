package com.example.bkjeon.base.api.v1.controller.example;

import com.example.bkjeon.base.api.v1.service.example.PostsService;
import com.example.bkjeon.base.api.v1.controller.example.dto.PostsResponseDTO;
import com.example.bkjeon.base.api.v1.controller.example.dto.PostsSaveRequestDTO;
import com.example.bkjeon.base.api.v1.controller.example.dto.PostsUpdateRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostsService postsService;

    @GetMapping("api/v1/posts/{id}")
    public PostsResponseDTO findById(@PathVariable Long id) {
        return postsService.findById(id);
    }

    @PostMapping("api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDTO requestDTO) {
        return postsService.save(requestDTO);
    }

    @PutMapping("api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDTO requestDTO) {
        return postsService.update(id, requestDTO);
    }

    @DeleteMapping("api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }

}
