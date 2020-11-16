package com.ticket.captain.festivalCategory.dto;

import com.ticket.captain.festivalCategory.FestivalCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class FestivalCategoryCreateDto {

    @NotBlank
    private String categoryName;

    private LocalDateTime createDate;

    private Long createId;

    private LocalDateTime modifyDate;

    private Long modifyId;

    @Builder
    private FestivalCategoryCreateDto(String categoryName, LocalDateTime createDate, Long createId, LocalDateTime modifyDate, Long modifyId) {
        this.categoryName = categoryName;
        this.createDate = createDate;
        this.createId = createId;
        this.modifyDate = modifyDate;
        this.modifyId = modifyId;
    }

    public FestivalCategoryCreateDto toDto() {

        return FestivalCategoryCreateDto.builder()
                .categoryName(categoryName)
                .createDate(createDate)
                .createId(createId)
                .modifyDate(modifyDate)
                .modifyId(modifyId)
                .build();
    }

    public FestivalCategory toEntity() {

        return FestivalCategory.builder()
                .categoryName(categoryName)
                .createDate(createDate)
                .createId(createId)
                .modifyDate(modifyDate)
                .modifyId(modifyId)
                .build();
    }
}
