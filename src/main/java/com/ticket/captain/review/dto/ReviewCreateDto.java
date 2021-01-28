package com.ticket.captain.review.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class ReviewCreateDto {

    private String title;

    private String contents;

    private Long festivalId;

    @Builder
    public ReviewCreateDto(String title, String contents, Long festivalId) {
        this.title = title;
        this.contents = contents;
        this.festivalId = festivalId;
    }
}
