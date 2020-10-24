package com.ticket.captain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewUpdateRequestDto {

    @NotBlank
    private Long reviewId;

    @NotBlank
    @Length(min=2, max=100)
    private String content;

}
