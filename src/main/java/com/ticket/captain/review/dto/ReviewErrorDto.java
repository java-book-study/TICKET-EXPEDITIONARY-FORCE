package com.ticket.captain.review.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewErrorDto {

    private String message;

    public static ReviewErrorDto of(RuntimeException e) {
        return ReviewErrorDto.builder()
                .message(e.getMessage())
                .build();
    }
}
