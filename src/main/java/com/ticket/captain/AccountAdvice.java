package com.ticket.captain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.ticket.captain.account")
@Slf4j
public class AccountAdvice {

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity nullPointException(RuntimeException e){

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "존재하지 않는 회원입니다.");

        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

}
