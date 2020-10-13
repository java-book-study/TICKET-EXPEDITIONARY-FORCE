package com.ticket.captain.account;

import com.ticket.captain.response.ApiResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.ticket.captain.account")
@Slf4j
public class AccountAdvice {

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiResponseDto nullPointException(RuntimeException e) {
        return ApiResponseDto.NOT_FOUND(e);
    }

}
