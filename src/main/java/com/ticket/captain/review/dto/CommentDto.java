package com.ticket.captain.review.dto;

import com.ticket.captain.review.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentDto {

    private Long id;

    private String contents;

    private String writer;

    private Integer level;

    private Boolean live;

    private Long reviewId;

    private Long superCommentId;

    private String createId;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    private String modifyId;

    @Builder
    public CommentDto(Long id, String contents, String writer, Integer level, Boolean live, Long reviewId, Long superCommentId,
                      String createId, LocalDateTime createDate, LocalDateTime modifyDate, String modifyId) {
        this.id = id;
        this.contents = contents;
        this.writer = writer;
        this.level = level;
        this.live = live;
        this.reviewId = reviewId;
        this.superCommentId = superCommentId;
        this.createId = createId;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.modifyId = modifyId;
    }

    public static CommentDto of(Comment comment) {

        return CommentDto.builder()
                .id(comment.getId())
                .contents(comment.getContents())
                .level(comment.getLevel())
                .writer(comment.getWriter())
                .live(comment.getLive())
                .reviewId(comment.getReview().getId())
                .superCommentId(comment.getSuperComment().getId())
                .createDate(comment.getCreateDate())
                .createId(comment.getCreateId())
                .modifyDate(comment.getModifyDate())
                .modifyId(comment.getModifyId())
                .build();

    }
}
