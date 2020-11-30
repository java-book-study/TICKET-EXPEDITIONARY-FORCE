package com.ticket.captain.festival.dto;

import com.ticket.captain.festival.Festival;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class FestivalDto {

    private Long id;

    private String title;

    private String thumbnail;

    private String content;

    private LocalDateTime salesStartDate;

    private LocalDateTime salesEndDate;

    private Long createId;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    private Long modifyId;

    private String festivalCategory;

    @Builder
    private FestivalDto(Long id, String title, String thumbnail, String content,
                        LocalDateTime salesStartDate, LocalDateTime salesEndDate,
                        Long createId, LocalDateTime createDate,
                        LocalDateTime modifyDate, Long modifyId, String festivalCategory) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.content = content;
        this.salesStartDate = salesStartDate;
        this.salesEndDate = salesEndDate;
        this.createId = createId;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.modifyId = modifyId;
        this.festivalCategory = festivalCategory;
    }

    public static FestivalDto of(Festival festival) {

        return FestivalDto.builder()
                .id(festival.getId())
                .title(festival.getTitle())
                .content(festival.getContent())
                .thumbnail(festival.getThumbnail())
                .createDate(festival.getCreateDate())
                .createId(festival.getCreateId())
                .modifyDate(festival.getModifyDate())
                .modifyId(festival.getModifyId())
                .salesEndDate(festival.getSalesEndDate())
                .salesStartDate(festival.getSalesStartDate())
                .festivalCategory(festival.getFestivalCategory())
                .build();
    }
}
