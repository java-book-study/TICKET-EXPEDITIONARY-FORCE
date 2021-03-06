package com.ticket.captain.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentUpdateDto {

    private String contents;

    private Long reviewId;

    private Long commentId;

    public CommentUpdateDto(String contents, Long reviewId, Long commentId) {
        this.contents = contents;
        this.reviewId = reviewId;
        this.commentId = commentId;
    }
}
