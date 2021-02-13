package com.ticket.captain.review.dto;

import com.ticket.captain.review.Comment;
import com.ticket.captain.review.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ReviewCommentDto {

    private Long id;

    private String title;

    private String contents;

    private String writer;

    private int commentCount;

    private Long festivalId;

    private List<CommentDto> comments = new ArrayList<>();

    @Builder
    private ReviewCommentDto(Long id, String title, String contents, String writer,
                            int commentCount, Long festivalId, List<CommentDto> comments) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.writer = writer;
        this.commentCount = commentCount;
        this.festivalId = festivalId;
        this.comments = comments;
    }

    public static ReviewCommentDto of(Review review) {
        List<CommentDto> comments = review.getComments().stream()
                .map(CommentDto::of)
                .collect(Collectors.toList());

        return ReviewCommentDto.builder()
                .id(review.getId())
                .title(review.getTitle())
                .contents(review.getContents())
                .writer(review.getWriter())
                .commentCount(review.getCommentCount())
                .festivalId(review.getFestival().getId())
                .comments(comments)
                .build();
    }


}
