package com.ticket.captain.festivalCategory.dto;

import com.ticket.captain.festivalCategory.FestivalCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FestivalCategoryUpdateDto {

    @NotBlank
    private String categoryName;

    @NotBlank
    private LocalDateTime modifyDate;

    @NotBlank
    private Long modifyId;

    @Builder
    private FestivalCategoryUpdateDto(String categoryName, LocalDateTime modifyDate, Long modifyId) {
        this.categoryName = categoryName;
        this.modifyDate = modifyDate;
        this.modifyId = modifyId;
    }

    public FestivalCategoryUpdateDto toDto() {

        return FestivalCategoryUpdateDto.builder()
                .categoryName(categoryName)
                .modifyDate(modifyDate)
                .modifyId(modifyId)
                .build();
    }

    public void apply(FestivalCategory festivalCategory) {
        festivalCategory.update(categoryName, modifyDate, modifyId);
    }
}
