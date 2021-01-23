package com.ticket.captain.review;

import com.ticket.captain.review.dto.CommentDto;
import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class CommentResource extends EntityModel<CommentDto> {

    public static EntityModel<CommentDto> of(CommentDto commentDto) {
        return EntityModel.of(commentDto).add(linkTo(CommentRestController.class).slash(commentDto.getId()).withSelfRel());
    }
}
