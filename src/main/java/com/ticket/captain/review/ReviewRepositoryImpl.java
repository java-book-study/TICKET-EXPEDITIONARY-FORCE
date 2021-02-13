package com.ticket.captain.review;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ticket.captain.account.QAccount;
import com.ticket.captain.festival.QFestival;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Review findReviewByIdWithComments(long reviewId) {
        return jpaQueryFactory.selectFrom(QReview.review)
                .join(QReview.review.comments, QComment.comment).fetchJoin()
                .leftJoin(QReview.review.account, QAccount.account).fetchJoin()
                .leftJoin(QReview.review.festival, QFestival.festival).fetchJoin()
                .where(QReview.review.id.eq(reviewId))
                .fetch()
                .get(0);
    }
}
