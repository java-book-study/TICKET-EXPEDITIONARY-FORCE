package com.ticket.captain.review.dto;

import com.ticket.captain.festival.Festival;
import com.ticket.captain.review.Review;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;

import static org.springframework.beans.BeanUtils.copyProperties;

@Getter @Setter
public class ReviewResponseDto {

    private Long id;

    private int star_score;

    private String content;

    private LocalDateTime createAt;

    private int sympathy;

    private int no_sympathy;

    private Festival festival;

    public ReviewResponseDto(Review source) {
        copyProperties(source, this);
        this.festival = source.getFestival();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("star_score", star_score)
                .append("content", content)
                .append("createAt", createAt)
                .append("sympathy", sympathy)
                .append("no_sympathy", no_sympathy)
                .append("festival", festival)
                .toString();
    }
}
