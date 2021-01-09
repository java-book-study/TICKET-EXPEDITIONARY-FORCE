package com.ticket.captain.ticket.dto;

import com.ticket.captain.ticket.Ticket;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketDto {
    private Long ticketId;

    private String ticketNo;

    private Long orderId;

    private Long festivalDetailId;

    private String statusCode;

    private BigDecimal price;

    private LocalDateTime createDate;

    private String createId;

    private LocalDateTime modifyDate;

    private String modifyId;

    @Builder
    private TicketDto(Long ticketId, String ticketNo, Long orderId, Long festivalDetailId,
                      String statusCode, BigDecimal price, LocalDateTime createDate, String createId,
                      LocalDateTime modifyDate, String modifyId) {
        this.ticketId = ticketId;
        this.ticketNo = ticketNo;
        this.orderId = orderId;
        this.festivalDetailId = festivalDetailId;
        this.statusCode = statusCode;
        this.price = price;
        this.createDate = createDate;
        this.createId = createId;
        this.modifyDate = modifyDate;
        this.modifyId = modifyId;
    }

    public static TicketDto of(Ticket ticket) {
        return TicketDto.builder()
                .ticketId(ticket.getId())
                .ticketNo(ticket.getTicketNo())
                .festivalDetailId(ticket.getFestivalDetail().getId())
                .statusCode(ticket.getStatusCode())
                .price(ticket.getPrice())
                .createDate(ticket.getCreateDate())
                .createId(ticket.getCreateId())
                .modifyDate(ticket.getModifyDate())
                .modifyId(ticket.getModifyId())
                .build();
    }
}