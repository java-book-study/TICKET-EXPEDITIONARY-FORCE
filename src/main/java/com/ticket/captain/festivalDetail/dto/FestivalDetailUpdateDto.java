package com.ticket.captain.festivalDetail.dto;

import com.ticket.captain.festival.Festival;
import com.ticket.captain.festivalDetail.FestivalDetail;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class FestivalDetailUpdateDto {

    private String salesType;

    private Long amount;

    private Long price;

    private LocalDateTime processDate;

    private LocalDateTime drawDate;

    @Builder
    private FestivalDetailUpdateDto(String salesType, Long amount, Long price,
                                    LocalDateTime processDate, LocalDateTime drawDate) {
        this.salesType = salesType;
        this.amount = amount;
        this.price = price;
        this.processDate = processDate;
        this.drawDate = drawDate;
    }

    public void apply(FestivalDetail festivalDetail) {
        festivalDetail.update(salesType, amount, price, processDate, drawDate);
    }
}
