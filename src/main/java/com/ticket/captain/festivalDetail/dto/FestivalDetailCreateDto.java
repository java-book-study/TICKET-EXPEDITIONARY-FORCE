package com.ticket.captain.festivalDetail.dto;

import com.ticket.captain.festival.Festival;
import com.ticket.captain.festivalDetail.FestivalDetail;
import com.ticket.captain.salesType.SalesType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class FestivalDetailCreateDto {

    @NotBlank
    private LocalDateTime processDate;

    @NotBlank
    private Long amount;

    @NotBlank
    private Long price;

    @NotBlank
    private LocalDateTime drawDate;

    @NotBlank
    private LocalDateTime createDate;

    @NotBlank
    private Long createId;

    private LocalDateTime modifyDate;

    private Long modifyId;

    private Festival festival;

    private SalesType salesType;

    @Builder
    private FestivalDetailCreateDto(LocalDateTime processDate, Long amount, Long price, LocalDateTime drawDate, LocalDateTime createDate, Long createId, LocalDateTime modifyDate, Long modifyId, Festival festival, SalesType salesType) {
        this.processDate = processDate;
        this.amount = amount;
        this.price = price;
        this.drawDate = drawDate;
        this.createDate = createDate;
        this.createId = createId;
        this.modifyDate = modifyDate;
        this.modifyId = modifyId;
        this.festival = festival;
        this.salesType = salesType;
    }


    public FestivalDetailCreateDto toDto() {

        return FestivalDetailCreateDto.builder()
                .processDate(processDate)
                .amount(amount)
                .price(price)
                .createDate(createDate)
                .createId(createId)
                .drawDate(drawDate)
                .modifyDate(modifyDate)
                .modifyId(modifyId)
                .festival(festival)
                .salesType(salesType)
                .build();
    }

    public FestivalDetail toEntity() {

        return FestivalDetail.builder()
                .amount(amount)
                .price(price)
                .festival(festival)
                .build();
    }
}
