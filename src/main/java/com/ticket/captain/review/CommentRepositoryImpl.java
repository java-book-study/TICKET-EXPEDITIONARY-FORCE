package com.ticket.captain.review;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ticket.captain.account.QAccount;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Comment> findReviewByIdWithComments(long reviewId) {
        return jpaQueryFactory.selectFrom(QComment.comment)
                .join(QComment.comment.review, QReview.review).fetchJoin()
                .leftJoin(QComment.comment.account, QAccount.account).fetchJoin()
                .where(QReview.review.id.eq(reviewId))
                .fetch();
    }
}
