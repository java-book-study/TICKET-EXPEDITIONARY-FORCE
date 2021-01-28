package com.ticket.captain.review;

import com.ticket.captain.account.Account;
import com.ticket.captain.account.AccountRepository;
import com.ticket.captain.exception.NotFoundException;
import com.ticket.captain.exception.UnauthorizedException;
import com.ticket.captain.festival.Festival;
import com.ticket.captain.festival.FestivalRepository;
import com.ticket.captain.review.dto.ReviewCreateDto;
import com.ticket.captain.review.dto.ReviewDto;
import com.ticket.captain.review.dto.ReviewUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final AccountRepository accountRepository;
    private final FestivalRepository festivalRepository;

    public ReviewDto add(ReviewCreateDto reviewCreateDto, Long accountId, Long festivalId) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(NotFoundException::new);
        Festival festival = festivalRepository.findById(festivalId)
                .orElseThrow(NotFoundException::new);

        Review newReview = of(reviewCreateDto, account, festival);
        account.getReviews().add(newReview);
        festival.getReviews().add(newReview);
        accountRepository.save(account);
        festivalRepository.save(festival);

        return ReviewDto.of(reviewRepository.save(newReview));
    }

    private Review of(ReviewCreateDto reviewCreateDto, Account account, Festival festival) {
        return Review.builder()
                .title(reviewCreateDto.getTitle())
                .contents(reviewCreateDto.getContents())
                .writer(account.getName())
                .commentCount(0)
                .account(account)
                .festival(festival)
                .build();
    }

    @Transactional(readOnly = true)
    public ReviewDto findById(Long reviewId) {
        return ReviewDto.of(
                reviewRepository.findById(reviewId).orElseThrow(NotFoundException::new));
    }

    @Transactional(readOnly = true)
    public Page<ReviewDto> findAll(Pageable pageable) {
        return reviewRepository.findAll(pageable).map(ReviewDto::of);
    }

    public ReviewDto update(ReviewUpdateDto reviewUpdateDto, Long accountId) {
        Review review = reviewRepository.findById(reviewUpdateDto.getReviewId())
                .orElseThrow(NotFoundException::new);

        if (!accountId.equals(review.getAccount().getId())) {
            throw new UnauthorizedException();
        }

        review.apply(reviewUpdateDto.getTitle(), reviewUpdateDto.getContents());
        return ReviewDto.of(reviewRepository.save(review));
    }

    public void delete(Long reviewId, Long accountId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(NotFoundException::new);

        if (!accountId.equals(review.getAccount().getId())) {
            throw new UnauthorizedException();
        }

        reviewRepository.delete(review);
    }
}
