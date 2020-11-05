package com.ticket.captain.ticket.dto;

import com.ticket.captain.ticket.Ticket;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketCreateDto {
    private String ticketNo;
    private String orderNo;
    private Long festivalId;
    private Long festivalSq;
    private Long statusCode;
    private BigDecimal price;

    @Builder
    private TicketCreateDto(String ticketNo, String orderNo, Long festivalId, Long festivalSq, Long statusCode, BigDecimal price) {
        this.ticketNo = ticketNo;
        this.orderNo = orderNo;
        this.festivalId = festivalId;
        this.festivalSq = festivalSq;
        this.statusCode = statusCode;
        this.price = price;
    }

    public TicketCreateDto toDto() {
        return TicketCreateDto.builder()
                .ticketNo(ticketNo)
                .orderNo(orderNo)
                .festivalId(festivalId)
                .festivalSq(festivalSq)
                .statusCode(statusCode)
                .price(price)
                .build();
    }

    public Ticket toEntity() {
        return Ticket.builder()
                .ticketNo(ticketNo)
                .orderNo(orderNo)
                .festivalId(festivalId)
                .festivalSq(festivalSq)
                .statusCode(statusCode)
                .price(price)
                .build();
    }
}
