package com.ticket.captain.festivalDetail.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ticket.captain.festival.Festival;
import com.ticket.captain.festivalDetail.FestivalDetail;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class FestivalDetailDto {

    private Long id;

    private String salesType;

    private Long amount;

    private Long price;

    private LocalDateTime processDate;

    private LocalDateTime drawDate;

    private LocalDateTime createDate;

    private String createId;

    private LocalDateTime modifyDate;

    private String modifyId;

    @JsonIgnore
    private Festival festival;

    @Builder
    private FestivalDetailDto(Long id, String salesType, Long amount, Long price,
                              LocalDateTime processDate, LocalDateTime drawDate,
                              LocalDateTime createDate, LocalDateTime modifyDate,
                              String createId, String modifyId, Festival festival) {
        this.id = id;
        this.salesType = salesType;
        this.amount = amount;
        this.price = price;
        this.processDate = processDate;
        this.drawDate = drawDate;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.createId = createId;
        this.modifyId = modifyId;
        this.festival = festival;
    }

    public static FestivalDetailDto of(FestivalDetail festivalDetail) {

        return FestivalDetailDto.builder()
                .id(festivalDetail.getId())
                .salesType(festivalDetail.getSalesType())
                .amount(festivalDetail.getAmount())
                .price(festivalDetail.getPrice())
                .processDate(festivalDetail.getProcessDate())
                .drawDate(festivalDetail.getDrawDate())
                .createDate(festivalDetail.getCreateDate())
                .modifyDate(festivalDetail.getModifyDate())
                .createId(festivalDetail.getCreateId())
                .modifyId(festivalDetail.getModifyId())
                .festival(festivalDetail.getFestival())
                .build();
    }
}
