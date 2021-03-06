package com.ticket.captain.festival.dto;

import com.ticket.captain.festival.Festival;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * api등에서 return할 때에 쓰이는 Dto
 * Festival를 save 후에 반납을 FestivalDto 등으로 받을 때에 들어간다.
 * 따라서 모든 필드 값을 가지고 있다.
 */
@Getter
@NoArgsConstructor
public class FestivalDto {

    private Long id;

    private String title;

    private String thumbnail;

    private String content;

    private LocalDateTime salesStartDate;

    private LocalDateTime salesEndDate;

    private String createId;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    private String modifyId;

    private String festivalCategory;

    @Builder
    private FestivalDto(Long id, String title, String thumbnail, String content,
                        LocalDateTime salesStartDate, LocalDateTime salesEndDate,
                        String createId, LocalDateTime createDate,
                        LocalDateTime modifyDate, String modifyId, String festivalCategory) {
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
