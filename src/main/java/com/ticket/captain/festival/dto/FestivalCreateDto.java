package com.ticket.captain.festival.dto;

import com.ticket.captain.festival.Festival;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class FestivalCreateDto {

    private String title;

    private String thumbnail;

    private String content;

    private LocalDateTime salesStartDate;

    private LocalDateTime salesEndDate;

    private String festivalCategory;

    @Builder
    public FestivalCreateDto(String title, String thumbnail, String content,
                             LocalDateTime salesStartDate, LocalDateTime salesEndDate, String festivalCategory) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.content = content;
        this.salesStartDate = salesStartDate;
        this.salesEndDate = salesEndDate;
        this.festivalCategory = festivalCategory;
    }

    public FestivalCreateDto toDto() {

        return FestivalCreateDto.builder()
                .title(title)
                .content(content)
                .thumbnail(thumbnail)
                .salesEndDate(salesEndDate)
                .salesStartDate(salesStartDate)
                .festivalCategory(festivalCategory)
                .build();
    }

    public Festival toEntity() {

        return Festival.builder()
                .title(title)
                .content(content)
                .thumbnail(thumbnail)
                .salesStartDate(salesStartDate)
                .salesEndDate(salesEndDate)
                .festivalCategory(festivalCategory)
                .build();
    }
}
