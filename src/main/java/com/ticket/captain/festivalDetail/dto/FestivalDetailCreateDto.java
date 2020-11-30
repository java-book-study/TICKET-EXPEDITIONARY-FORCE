package com.ticket.captain.festivalDetail.dto;

import com.ticket.captain.festivalDetail.FestivalDetail;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class FestivalDetailCreateDto {

    private String salesType;

    private Long amount;

    private Long price;

    private LocalDateTime processDate;

    private LocalDateTime drawDate;


    @Builder
    private FestivalDetailCreateDto(String salesType, Long amount, Long price,
                                    LocalDateTime drawDate,LocalDateTime processDate) {
        this.salesType = salesType;
        this.amount = amount;
        this.price = price;
        this.processDate = processDate;
        this.drawDate = drawDate;
    }


    public FestivalDetailCreateDto toDto() {

        return FestivalDetailCreateDto.builder()
                .salesType(salesType)
                .amount(amount)
                .price(price)
                .drawDate(drawDate)
                .processDate(processDate)
                .build();
    }

    public FestivalDetail toEntity() {

        return FestivalDetail.builder()
                .salesType(salesType)
                .amount(amount)
                .price(price)
                .drawDate(drawDate)
                .processDate(processDate)
                .build();
    }
}
