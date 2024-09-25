package com.example.bkjeon.base.repository.example;


import com.example.bkjeon.base.domain.example.Board;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

import static com.example.bkjeon.base.domain.example.QBoard.board;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Board> findByAuthor(String author) {
        return queryFactory.selectFrom(board)
            .where(board.author.eq(author))
            .fetch();
    }

}
