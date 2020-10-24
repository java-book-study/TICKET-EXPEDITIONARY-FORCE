package com.ticket.captain.review;

import com.ticket.captain.exception.NotFoundException;
import com.ticket.captain.festival.Festival;
import com.ticket.captain.festival.FestivalRepository;
import com.ticket.captain.review.dto.ReviewCreateDto;
import com.ticket.captain.review.dto.ReviewUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.google.common.base.Preconditions.checkNotNull;

@Transactional
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final FestivalRepository festivalRepository;

    public Review write(ReviewCreateDto reviewCreateDto) {
        Festival festival = festivalRepository.findById(reviewCreateDto.getFestivalId())
                .orElseThrow(NotFoundException::new);

        Review newReview = Review.builder()
                .content(reviewCreateDto.getContent())
                .createAt(LocalDateTime.now())
                .star_score(0)
                .sympathy(0)
                .no_sympathy(0)
                .festival(festival)
                .build();

        return newReview;
    }

    public Review updateReview(Long reviewId, ReviewUpdateRequestDto reviewUpdateRequestDto) {
        checkNotNull(reviewId, "reviewId must be provided.");
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(NotFoundException::new);
        review.update(reviewUpdateRequestDto);
        return review;
    }

    public void starUpdate(Long reviewId) {
        checkNotNull(reviewId, "reviewId must be provided.");
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(NotFoundException::new);
        review.starUpdate();
    }

    public void sympathyUpdate(Long reviewId) {
        checkNotNull(reviewId, "reviewId must be provided.");
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(NotFoundException::new);
        review.sympathyUpdate();
    }

    public void no_sympathyUpdate(Long reviewId) {
        checkNotNull(reviewId, "reviewId must be provided.");
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(NotFoundException::new);
        review.no_sympathyUpdate();
    }

    public void reviewDelete(Long reviewId) {
        checkNotNull(reviewId, "reviewId must be provided.");
        reviewRepository.deleteById(reviewId);
    }
}
