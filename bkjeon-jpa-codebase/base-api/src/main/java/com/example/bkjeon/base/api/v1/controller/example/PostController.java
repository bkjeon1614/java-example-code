package com.example.bkjeon.base.api.v1.controller.example;

import com.example.bkjeon.base.api.v1.service.example.PostsService;
import com.example.bkjeon.base.api.v1.controller.example.dto.PostsResponseDTO;
import com.example.bkjeon.base.api.v1.controller.example.dto.PostsSaveRequestDTO;
import com.example.bkjeon.base.api.v1.controller.example.dto.PostsUpdateRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostsService postsService;

    @GetMapping("{id}")
    public PostsResponseDTO postFindById(@PathVariable Long id) {
        return postsService.findById(id);
    }

    @PostMapping
    public Long postSave(@RequestBody PostsSaveRequestDTO requestDTO) {
        return postsService.save(requestDTO);
    }

    @PutMapping("{id}")
    public Long postUpdate(@PathVariable Long id, @RequestBody PostsUpdateRequestDTO requestDTO) {
        return postsService.update(id, requestDTO);
    }

    @DeleteMapping("{id}")
    public Long postDelete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }

}
