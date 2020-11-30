package com.ticket.captain.festivalDetail.dto;

import com.ticket.captain.festival.Festival;
import com.ticket.captain.festivalDetail.FestivalDetail;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * FestivalDetailCreateDto 에서 festival를 넣어준다는 것으로 가정
 * 이후 toEntity를 통하여 Festival 엔티티를 생성
 */
@Getter
@NoArgsConstructor
public class FestivalDetailCreateDto {

    private String salesType;

    private Long amount;

    private Long price;

    private LocalDateTime processDate;

    private LocalDateTime drawDate;

    private Festival festival;

    public FestivalDetail toEntity() {

        return FestivalDetail.builder()
                .salesType(salesType)
                .amount(amount)
                .price(price)
                .drawDate(drawDate)
                .processDate(processDate)
                .festival(festival)
                .build();
    }
}
