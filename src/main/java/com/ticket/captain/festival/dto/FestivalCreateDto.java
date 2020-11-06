package com.ticket.captain.festival.dto;

import com.ticket.captain.festival.Festival;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Builder
@Getter @Setter
@ToString
public class FestivalCreateDto {

    @NotBlank
    @Length(min = 2, max = 20)
    private String title;

    private String Thumbnail;

    @NotBlank
    @Length(min = 2, max = 1000)
    private String content;

    @NotBlank
    private LocalDateTime salesStartDate;

    @NotBlank
    private LocalDateTime salesEndDate;

    @NotBlank
    private Long createId;

    @NotBlank
    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    private Long modifyId;

    public Festival newFestival() {
        return new Festival(title, content, Thumbnail, salesStartDate, salesEndDate, createId, modifyDate, modifyId);
    }

}
