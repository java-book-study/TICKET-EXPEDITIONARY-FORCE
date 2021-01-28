package com.ticket.captain.review;

import com.ticket.captain.exception.NotFoundException;
import com.ticket.captain.exception.UnauthorizedException;
import com.ticket.captain.review.dto.ReviewDto;
import com.ticket.captain.review.dto.ReviewErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.ticket.captain.review")
@Slf4j
public class ReviewAdvice {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public EntityModel<ReviewErrorDto> notFoundException(RuntimeException e) {
        ReviewErrorDto errorDto = ReviewErrorDto.of(e);
        EntityModel<ReviewErrorDto> errorDtoEntityModel = ReviewResource.of(errorDto);
        errorDtoEntityModel.add(Link.of("/docs/index.html#not_found-review").withRel("profile"));
        return errorDtoEntityModel;
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public EntityModel<ReviewErrorDto> unAuthorizedException(RuntimeException e) {
        ReviewErrorDto errorDto = ReviewErrorDto.of(e);
        EntityModel<ReviewErrorDto> errorDtoEntityModel = ReviewResource.of(errorDto);
        errorDtoEntityModel.add(Link.of("/docs/index.html#unAuthorized-review").withRel("profile"));
        return errorDtoEntityModel;
    }
}
