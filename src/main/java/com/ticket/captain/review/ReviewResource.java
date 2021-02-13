package com.ticket.captain.review;

import com.ticket.captain.review.dto.ReviewCommentDto;
import com.ticket.captain.review.dto.ReviewDto;
import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class ReviewResource extends EntityModel<ReviewDto> {

    public static EntityModel<ReviewDto> of(ReviewDto reviewDto) {
        return EntityModel.of(reviewDto).add(linkTo(ReviewRestController.class).slash(reviewDto.getId()).withSelfRel());
    }

    public static EntityModel<ReviewCommentDto> of(ReviewCommentDto reviewDto) {
        return EntityModel.of(reviewDto).add(linkTo(ReviewRestController.class).slash(reviewDto.getId()).withSelfRel());
    }
}
