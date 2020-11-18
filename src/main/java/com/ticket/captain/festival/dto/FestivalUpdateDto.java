package com.ticket.captain.festival.dto;

import com.ticket.captain.festival.Festival;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class FestivalUpdateDto {

    @NotBlank
    private String title;

    private String thumbnail;

    @NotBlank
    private String content;

    @NotBlank
    private LocalDateTime salesStartDate;

    @NotBlank
    private LocalDateTime salesEndDate;

    @NotBlank
    private LocalDateTime modifyDate;

    @NotBlank
    private Long modifyId;

    @Builder
    private FestivalUpdateDto(String title, String thumbnail, String content, LocalDateTime salesStartDate, LocalDateTime salesEndDate, LocalDateTime modifyDate, Long modifyId) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.content = content;
        this.salesStartDate = salesStartDate;
        this.salesEndDate = salesEndDate;
        this.modifyDate = modifyDate;
        this.modifyId = modifyId;
    }

    public FestivalUpdateDto toDto() {

        return FestivalUpdateDto.builder()
                .title(title)
                .content(content)
                .thumbnail(thumbnail)
                .salesStartDate(salesStartDate)
                .salesEndDate(salesEndDate)
                .modifyDate(modifyDate)
                .modifyId(modifyId)
                .build();
    }

    public void apply(Festival festival) {
        festival.update(title, content, thumbnail, salesStartDate, salesEndDate, modifyDate, modifyId);
    }
}
