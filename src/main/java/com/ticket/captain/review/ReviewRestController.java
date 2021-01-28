package com.ticket.captain.review;

import com.ticket.captain.account.Account;
import com.ticket.captain.account.CurrentUser;
import com.ticket.captain.response.ApiResponseDto;
import com.ticket.captain.review.dto.ReviewCreateDto;
import com.ticket.captain.review.dto.ReviewDto;
import com.ticket.captain.review.dto.ReviewUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/review", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class ReviewRestController {

    private final ReviewService reviewService;

    @GetMapping("")
    public ResponseEntity<?> reviews(@PageableDefault Pageable pageable,
                                     PagedResourcesAssembler<ReviewDto> assembler) {
        Page<ReviewDto> reviews = reviewService.findAll(pageable);
        PagedModel<EntityModel<ReviewDto>> entityModels = assembler.toModel(reviews, ReviewResource::of);
        entityModels.add(Link.of("/docs/index.html#list-reviews").withRel("profile"));
        return ResponseEntity.ok(entityModels);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<?> reviewDetail(@PathVariable Long reviewId) {
        ReviewDto reviewDto = reviewService.findById(reviewId);
        EntityModel<ReviewDto> reviewDtoEntityModel = ReviewResource.of(reviewDto);
        reviewDtoEntityModel.add(Link.of("/docs/index.html#get-review").withRel("profile"));
        return ResponseEntity.ok(reviewDtoEntityModel);
    }

    @PostMapping("")
    public ResponseEntity<?> write(@RequestBody ReviewCreateDto createDto, @CurrentUser Account account) {
        ReviewDto reviewDto = reviewService.add(createDto, account.getId(), createDto.getFestivalId());
        EntityModel<ReviewDto> reviewDtoEntityModel = ReviewResource.of(reviewDto);
        reviewDtoEntityModel.add(Link.of("/docs/index.html#add-review").withRel("profile"));
        return ResponseEntity.ok(reviewDtoEntityModel);
    }

    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody ReviewUpdateDto reviewUpdateDto, @CurrentUser Account account) {
        ReviewDto reviewDto = reviewService.update(reviewUpdateDto, account.getId());
        EntityModel<ReviewDto> reviewDtoEntityModel = ReviewResource.of(reviewDto);
        reviewDtoEntityModel.add(Link.of("/docs/index.html#update-review").withRel("profile"));
        return ResponseEntity.ok(reviewDtoEntityModel);
    }

    @DeleteMapping("{reviewId}")
    public ApiResponseDto<String> delete(@PathVariable Long reviewId, @CurrentUser Account account) {
        reviewService.delete(reviewId, account.getId());
        return ApiResponseDto.DEFAULT_OK;
    }
}
