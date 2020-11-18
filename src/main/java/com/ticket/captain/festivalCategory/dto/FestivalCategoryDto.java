package com.ticket.captain.festivalCategory.dto;

import com.ticket.captain.festivalCategory.FestivalCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class FestivalCategoryDto {

    private Long id;

    private String categoryName;

    private LocalDateTime createDate;

    private Long createId;

    private LocalDateTime modifyDate;

    private Long modifyId;

    @Builder
    private FestivalCategoryDto(Long id, String categoryName, LocalDateTime createDate, Long createId, LocalDateTime modifyDate, Long modifyId) {
        this.id = id;
        this.categoryName = categoryName;
        this.createDate = createDate;
        this.createId = createId;
        this.modifyDate = modifyDate;
        this.modifyId = modifyId;
    }

    public static FestivalCategoryDto of(FestivalCategory festivalCategory) {

        return FestivalCategoryDto.builder()
                .id(festivalCategory.getId())
                .categoryName(festivalCategory.getCategoryName())
                .createId(festivalCategory.getCreateId())
                .createDate(festivalCategory.getCreateDate())
                .modifyId(festivalCategory.getModifyId())
                .modifyDate(festivalCategory.getModifyDate())
                .build();
    }
}
