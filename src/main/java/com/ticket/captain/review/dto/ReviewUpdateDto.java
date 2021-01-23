package com.ticket.captain.review.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewUpdateDto {

    private Long reviewId;

    private String title;

    private String contents;

    @Builder
    public ReviewUpdateDto(Long reviewId, String title, String contents) {
        this.reviewId = reviewId;
        this.title = title;
        this.contents = contents;
    }
}
