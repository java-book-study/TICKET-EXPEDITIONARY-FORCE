package com.ticket.captain.review;

import com.ticket.captain.festival.Festival;
import com.ticket.captain.review.dto.ReviewUpdateRequestDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@EqualsAndHashCode(of = "id")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int star_score;

    @Column
    @NotBlank
    @Length(min=2, max=100)
    private String content;

    @Column
    private LocalDateTime createAt;

    @Column
    private int sympathy;

    @Column
    private int no_sympathy;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Festival festival;

    //account 추가 ManyToOne

    public Review() {

    }

    public Review(Long id, int star_score, String content, LocalDateTime createAt, int sympathy, int no_sympathy, Festival festival) {
        this.id = id;
        this.star_score = star_score;
        this.content = content;
        this.createAt = createAt;
        this.sympathy = sympathy;
        this.no_sympathy = no_sympathy;
        this.festival = festival;
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

    public void update(ReviewUpdateRequestDto reviewUpdateRequestDto) {
        this.content = reviewUpdateRequestDto.getContent();
    }

    public void starUpdate() {
        ++star_score;
    }

    public void sympathyUpdate() {
        ++sympathy;
    }

    public void no_sympathyUpdate() {
        ++no_sympathy;
    }
}
