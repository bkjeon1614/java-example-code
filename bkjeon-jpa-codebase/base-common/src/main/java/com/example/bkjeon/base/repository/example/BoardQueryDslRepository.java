package com.example.bkjeon.base.repository.example;

import static com.example.bkjeon.base.entity.example.QBoard.board;

import com.example.bkjeon.base.domain.example.Board;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public List<Board> findByAuthor(String author) {
        return queryFactory.selectFrom(board)
            .where(board.author.eq(author))
            .fetch();
    }

}
