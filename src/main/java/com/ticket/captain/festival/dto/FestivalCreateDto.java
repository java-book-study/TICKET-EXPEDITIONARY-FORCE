package com.ticket.captain.festival.dto;

import com.ticket.captain.festival.Festival;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class FestivalCreateDto {

    @NotBlank
    @Length(min = 2, max = 20)
    private String title;

    private String thumbnail;

    @NotBlank
    @Length(min = 2, max = 1000)
    private String content;

    private LocalDateTime salesStartDate;

    private LocalDateTime salesEndDate;

    private Long createId;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    private Long modifyId;

    private Long categoryId;

    @Builder
    public FestivalCreateDto(@NotBlank @Length(min = 2, max = 20) String title, String thumbnail, @NotBlank @Length(min = 2, max = 1000) String content, LocalDateTime salesStartDate, LocalDateTime salesEndDate, Long createId, LocalDateTime createDate, LocalDateTime modifyDate, Long modifyId, Long categoryId) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.content = content;
        this.salesStartDate = salesStartDate;
        this.salesEndDate = salesEndDate;
        this.createId = createId;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.modifyId = modifyId;
        this.categoryId = categoryId;
    }

    public FestivalCreateDto toDto() {

        return FestivalCreateDto.builder()
                .title(title)
                .content(content)
                .thumbnail(thumbnail)
                .salesEndDate(salesEndDate)
                .salesStartDate(salesStartDate)
                .createDate(createDate)
                .createId(createId)
                .modifyDate(modifyDate)
                .modifyId(modifyId)
                .categoryId(categoryId)
                .build();
    }

    public Festival toEntity() {

        return Festival.builder()
                .title(title)
                .content(content)
                .thumbnail(thumbnail)
                .salesStartDate(salesStartDate)
                .salesEndDate(salesEndDate)
                .createDate(createDate)
                .createId(createId)
                .modifyDate(modifyDate)
                .modifyId(modifyId)
                .build();
    }
}
