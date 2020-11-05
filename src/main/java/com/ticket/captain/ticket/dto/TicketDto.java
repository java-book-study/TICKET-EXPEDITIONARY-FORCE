package com.ticket.captain.ticket.dto;

import com.ticket.captain.ticket.Ticket;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketDto {
    private Long ticketId;
    private String ticketNo;
    private String orderNo;
    private Long festivalId;
    private Long festivalSq;
    private Long statusCode;
    private BigDecimal price;

    @Builder
    private TicketDto(Long ticketId, String ticketNo, String orderNo, Long festivalId, Long festivalSq, Long statusCode, BigDecimal price) {
        this.ticketId = ticketId;
        this.ticketNo = ticketNo;
        this.orderNo = orderNo;
        this.festivalId = festivalId;
        this.festivalSq = festivalSq;
        this.statusCode = statusCode;
        this.price = price;
    }

    public static TicketDto of(Ticket ticket) {
        return TicketDto.builder()
                .ticketId(ticket.getTicketId())
                .ticketNo(ticket.getTicketNo())
                .orderNo(ticket.getOrderNo())
                .festivalId(ticket.getFestivalId())
                .festivalSq(ticket.getFestivalSq())
                .statusCode(ticket.getStatusCode())
                .price(ticket.getPrice())
                .build();
    }
}
