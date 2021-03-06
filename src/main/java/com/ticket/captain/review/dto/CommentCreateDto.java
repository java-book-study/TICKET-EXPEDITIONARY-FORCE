package com.ticket.captain.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentCreateDto {

    private String contents;

    private Long reviewId;

    private Long superCommentId;

    public CommentCreateDto(String contents, Long reviewId, Long superCommentId) {
        this.contents = contents;
        this.reviewId = reviewId;
        this.superCommentId = superCommentId;
    }
}
