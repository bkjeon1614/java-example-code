package com.example.bkjeon.base.repository.example;

import static com.example.bkjeon.base.entity.example.QBoard.board;

import com.example.bkjeon.base.entity.example.Board;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

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
