package com.ticket.captain.review;

import java.util.List;

public interface CommentRepositoryCustom {
    List<Comment> findReviewByIdWithComments(long reviewId);
}
