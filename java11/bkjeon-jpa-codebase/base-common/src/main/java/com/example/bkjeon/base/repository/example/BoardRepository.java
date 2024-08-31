package com.example.bkjeon.base.repository.example;

import com.example.bkjeon.base.domain.example.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {

}
