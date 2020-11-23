package com.ticket.captain.account;

import com.ticket.captain.account.dto.AccountErrorDto;
import com.ticket.captain.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
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
    public EntityModel<AccountErrorDto> notFoundException(RuntimeException e) {

        AccountErrorDto accountErrorDto = AccountErrorDto.builder().message(e.getMessage()).build();
        EntityModel<AccountErrorDto> accountErrorDtoModel = AccountResource.of(accountErrorDto);
        accountErrorDtoModel.add(Link.of("/docs/index.html#not_found-account").withRel("profile"));
        return accountErrorDtoModel;
    }

}
