package com.example.bkjeon.base.repository.example;

import com.example.bkjeon.base.entity.example.Board;
import java.util.List;

public interface BoardRepositoryCustom {
    List<Board> findByAuthor(String author);
}
