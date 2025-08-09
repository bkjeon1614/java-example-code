package com.bkjeon.codebase.adapter.out.persistence.repository;

import com.bkjeon.codebase.adapter.out.persistence.entity.QAdEntity;
import com.bkjeon.codebase.adapter.out.persistence.entity.QAdParticipationEntity;
import com.bkjeon.codebase.domain.model.AdParticipation;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdParticipationCustomRepositoryImpl implements AdParticipationCustomRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 광고 참여 조회
     * @param userId 유저 ID
     * @param startDate  광고 참여 시작일
     * @param endDate 광고 참여 종료일
     * @param offset 페이지 시작 위치
     * @param size 페이지 노출 사이즈
     * @return 광고 리스트
     */
    @Override
    public List<AdParticipation> findAdParticipationList(
        long userId, LocalDateTime startDate, LocalDateTime endDate, int offset, int size) {
        QAdParticipationEntity adParticipation = QAdParticipationEntity.adParticipationEntity;
        QAdEntity ad = QAdEntity.adEntity;

        return queryFactory
            .select(Projections.constructor(
                AdParticipation.class,
                adParticipation.id,
                adParticipation.userId,
                adParticipation.adId,
                adParticipation.participationTime,
                JPAExpressions.select(ad.name).from(ad).where(ad.id.eq(adParticipation.adId)),
                adParticipation.rewardAmount
            ))
            .from(adParticipation)
            .where(
                adParticipation.userId.eq(userId)
                    .and(adParticipation.participationTime.between(startDate, endDate))
            )
            .leftJoin(ad).on(adParticipation.adId.eq(ad.id))
            .orderBy(adParticipation.participationTime.asc())
            .offset(offset)
            .limit(size)
            .fetch();
    }

}