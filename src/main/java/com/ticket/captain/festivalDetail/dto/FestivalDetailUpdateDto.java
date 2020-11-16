package com.ticket.captain.festivalDetail.dto;

import com.ticket.captain.festivalDetail.FestivalDetail;
import com.ticket.captain.salesType.SalesType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class FestivalDetailUpdateDto {

    @NotBlank
    private LocalDateTime processDate;

    @NotBlank
    private Long amount;

    @NotBlank
    private Long price;

    @NotBlank
    private LocalDateTime drawDate;

    @NotBlank
    private LocalDateTime modifyDate;

    @NotBlank
    private Long modifyId;

    private SalesType salesType;

    @Builder
    private FestivalDetailUpdateDto(@NotBlank LocalDateTime processDate, @NotBlank Long amount, @NotBlank Long price, @NotBlank LocalDateTime drawDate, LocalDateTime modifyDate, Long modifyId, SalesType salesType) {
        this.processDate = processDate;
        this.amount = amount;
        this.price = price;
        this.drawDate = drawDate;
        this.modifyDate = modifyDate;
        this.modifyId = modifyId;
        this.salesType = salesType;
    }

    public FestivalDetailUpdateDto toDto() {

        return FestivalDetailUpdateDto.builder()
                .processDate(processDate)
                .amount(amount)
                .price(price)
                .drawDate(drawDate)
                .modifyDate(modifyDate)
                .modifyId(modifyId)
                .salesType(salesType)
                .build();
    }

    public void apply(FestivalDetail festivalDetail) {
        festivalDetail.update(processDate, amount, price, drawDate, modifyDate, modifyId, salesType);
    }
}
