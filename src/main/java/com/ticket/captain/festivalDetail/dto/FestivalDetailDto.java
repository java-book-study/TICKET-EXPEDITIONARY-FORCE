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
public class FestivalDetailDto {

    private Long id;

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
    private FestivalDetailDto(Long id, @NotBlank LocalDateTime processDate, @NotBlank Long amount, @NotBlank Long price, @NotBlank LocalDateTime drawDate, @NotBlank LocalDateTime createDate, @NotBlank Long createId, LocalDateTime modifyDate, Long modifyId, Festival festival, SalesType salesType) {
        this.id = id;
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

    public static FestivalDetailDto of(FestivalDetail festivalDetail) {

        return FestivalDetailDto.builder()
                .id(festivalDetail.getId())
                .processDate(festivalDetail.getProcessDate())
                .amount(festivalDetail.getAmount())
                .price(festivalDetail.getPrice())
                .drawDate(festivalDetail.getDrawDate())
                .createDate(festivalDetail.getCreateDate())
                .createId(festivalDetail.getCreateId())
                .modifyDate(festivalDetail.getModifyDate())
                .modifyId(festivalDetail.getModifyId())
                .festival(festivalDetail.getFestival())
                .salesType(festivalDetail.getSalesType())
                .build();
    }
}
