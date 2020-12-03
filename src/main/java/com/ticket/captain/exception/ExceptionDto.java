package com.ticket.captain.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ExceptionDto {
    private String message;

    @Builder
    private ExceptionDto(String message) {
        this.message = message;
    }

}
