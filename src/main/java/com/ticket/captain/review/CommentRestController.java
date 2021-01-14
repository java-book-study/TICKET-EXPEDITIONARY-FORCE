package com.ticket.captain.review;

import com.ticket.captain.account.Account;
import com.ticket.captain.account.CurrentUser;
import com.ticket.captain.response.ApiResponseDto;
import com.ticket.captain.review.dto.CommentCreateDto;
import com.ticket.captain.review.dto.CommentDto;
import com.ticket.captain.review.dto.CommentUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/comment", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class CommentRestController {

    private final CommentService commentService;

    @PostMapping("")
    public ResponseEntity<?> write(@RequestBody CommentCreateDto createDto, @CurrentUser Account account) {
        CommentDto commentDto = commentService.create(createDto, account.getId());
        EntityModel<CommentDto> commentDtoEntityModel = CommentResource.of(commentDto);
        commentDtoEntityModel.add(Link.of("/docs/index.html#add-comment").withRel("profile"));
        return ResponseEntity.ok(commentDtoEntityModel);
    }

    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody CommentUpdateDto updateDto, @CurrentUser Account account) {
        CommentDto commentDto = commentService.update(updateDto, account.getId());
        EntityModel<CommentDto> commentDtoEntityModel = CommentResource.of(commentDto);
        commentDtoEntityModel.add(Link.of("/docs/index.html#update-comment").withRel("profile"));
        return ResponseEntity.ok(commentDtoEntityModel);
    }

    @DeleteMapping("{commentId}")
    public ApiResponseDto<String> delete(@PathVariable Long commentId, @CurrentUser Account account) {
        commentService.delete(commentId, account.getId());
        return ApiResponseDto.DEFAULT_OK;
    }
}
