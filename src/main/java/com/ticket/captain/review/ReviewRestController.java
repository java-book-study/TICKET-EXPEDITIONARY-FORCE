package com.ticket.captain.review;

import com.ticket.captain.response.ApiResponseDto;
import com.ticket.captain.review.dto.ReviewCreateDto;
import com.ticket.captain.review.dto.ReviewResponseDto;
import com.ticket.captain.review.dto.ReviewUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/review")
@RequiredArgsConstructor
public class ReviewRestController {

    private final ReviewService reviewService;

    @PostMapping("write")
    public ApiResponseDto<ReviewResponseDto> review(@RequestBody ReviewCreateDto reviewCreateDto) {
        return ApiResponseDto.createOK(
                new ReviewResponseDto(reviewService.write(reviewCreateDto)));
    }

    @PutMapping("{reviewId}/update")
    public ApiResponseDto<ReviewResponseDto> updateReview(@PathVariable Long reviewId, ReviewUpdateRequestDto reviewUpdateRequestDto) {
        return ApiResponseDto.createOK(
                new ReviewResponseDto(reviewService.updateReview(reviewId, reviewUpdateRequestDto))
        );
    }

    @PostMapping("{reviewId}/delete")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.reviewDelete(reviewId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    //count 증가 method 만들기
}
