package com.example.bkjeon.base.api.v1.service.example;

import com.example.bkjeon.base.entity.example.Board;
import com.example.bkjeon.base.repository.example.BoardQueryDslRepository;
import com.example.bkjeon.base.repository.example.BoardRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExampleService {

    private final BoardRepository boardRepository;
    private final BoardQueryDslRepository boardQueryDslRepository;

    public List<Board> getBoardList() {
        List<Board> result = null;

        try {
            result = boardRepository.findByAuthor("bkjeon");
        } catch (Exception e) {
            log.error("getOliveOneSampleData Error: {}", e);
        }

        return result;
    }

    public List<Board> getQueryDslBoardList() {
        List<Board> result = null;

        try {
            result = boardQueryDslRepository.findByAuthor("bkjeon");
        } catch (Exception e) {
            log.error("getOliveOneSampleData Error: {}", e);
        }

        return result;
    }

}