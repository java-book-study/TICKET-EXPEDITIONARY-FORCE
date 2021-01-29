package com.ticket.captain.review;

public interface ReviewRepositoryCustom {
    Review findReviewByIdWithComments(long reviewId);
}
