package com.ticket.captain.festival.dto;

import com.ticket.captain.festival.Festival;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * FestivalService에서 update 처리에 사용되는 Dto
 * 내부의 apply 메서드를 사용해서 Festival 값을 update 시킨다.
 */
@Getter
@NoArgsConstructor
public class FestivalUpdateDto {

    private String title;

    private String thumbnail;

    private String content;

    private LocalDateTime salesStartDate;

    private LocalDateTime salesEndDate;

    private String festivalCategory;

    /*
    modifyId 에 대한 처리 해주어야함
     */

    @Builder
    private FestivalUpdateDto(String title, String thumbnail, String content,
                              LocalDateTime salesStartDate, LocalDateTime salesEndDate, String festivalCategory) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.content = content;
        this.salesStartDate = salesStartDate;
        this.salesEndDate = salesEndDate;
        this.festivalCategory = festivalCategory;
    }

    public FestivalUpdateDto toDto() {

        return FestivalUpdateDto.builder()
                .title(title)
                .content(content)
                .thumbnail(thumbnail)
                .salesStartDate(salesStartDate)
                .salesEndDate(salesEndDate)
                .festivalCategory(festivalCategory)
                .build();
    }

    public void apply(Festival festival) {
        festival.update(title, content, thumbnail, salesStartDate, salesEndDate, festivalCategory);
    }
}
