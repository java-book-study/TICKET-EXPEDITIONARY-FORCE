package com.ticket.captain.account;

import com.ticket.captain.exception.NotFoundException;
import com.ticket.captain.response.ApiResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 은성님 코드
 */
@RestControllerAdvice(basePackages = "com.ticket.captain.account")
@Slf4j
public class AccountAdvice {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiResponseDto<?> notFoundException(RuntimeException e) {
        return ApiResponseDto.NOT_FOUND(e);
    }

}
