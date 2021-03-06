package com.ticket.captain.review.dto;

import com.ticket.captain.review.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReviewDto {

    private Long id;

    private String title;

    private String contents;

    private String writer;

    private int commentCount;

    private Long festivalId;

    private String createId;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    private String modifyId;

    @Builder
    private ReviewDto(Long id, String title, String contents, String writer, int commentCount,
                     Long festivalId, String createId, LocalDateTime createDate, LocalDateTime modifyDate, String modifyId) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.writer = writer;
        this.commentCount = commentCount;
        this.festivalId = festivalId;
        this.createId = createId;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.modifyId = modifyId;
    }

    public static ReviewDto of(Review review) {

        return ReviewDto.builder()
                .id(review.getId())
                .title(review.getTitle())
                .writer(review.getWriter())
                .contents(review.getContents())
                .commentCount(review.getCommentCount())
                .createId(review.getCreateId())
                .createDate(review.getCreateDate())
                .modifyId(review.getModifyId())
                .modifyDate(review.getModifyDate())
                .festivalId(review.getFestival().getId())
                .build();
    }
}
