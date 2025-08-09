package com.bkjeon.codebase.adapter.out.persistence.repository;

import com.bkjeon.codebase.adapter.out.persistence.entity.AdEntity;
import com.bkjeon.codebase.adapter.out.persistence.entity.QAdEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdCustomRepositoryImpl implements AdCustomRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 광고 조회
     * @param size 총 조회할 개수
     * @return 광고 리스트
     */
    @Override
    public List<AdEntity> findTopAvailableAdsByLimit(int size) {
        QAdEntity ad = QAdEntity.adEntity;
        return queryFactory
            .selectFrom(ad)
            .where(
                ad.participationLimit.gt(0),
                ad.startDate.loe(LocalDateTime.now()),
                ad.endDate.goe(LocalDateTime.now())
            )
            .orderBy(ad.rewardAmount.desc())
            .limit(size)
            .fetch();
    }

}