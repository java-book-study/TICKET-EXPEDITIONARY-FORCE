package com.ticket.captain.ticket.dto;

import com.ticket.captain.order.StatusCode;
import com.ticket.captain.ticket.Ticket;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketCreateDto {
    private String ticketNo;
    private Long festivalId;
    private Long festivalSq;
    private StatusCode statusCode;
    private Long price;

    @Builder
    private TicketCreateDto(String ticketNo, Long festivalId, Long festivalSq, StatusCode statusCode, Long price) {
        this.ticketNo = ticketNo;
        this.festivalId = festivalId;
        this.festivalSq = festivalSq;
        this.statusCode = statusCode;
        this.price = price;
    }

    public TicketCreateDto toDto() {
        return TicketCreateDto.builder()
                .ticketNo(ticketNo)
                .festivalId(festivalId)
                .festivalSq(festivalSq)
                .statusCode(statusCode)
                .price(price)
                .build();
    }

    public Ticket toEntity() {
        return Ticket.builder()
                .ticketNo(ticketNo)
                .festivalId(festivalId)
                .festivalSq(festivalSq)
                .statusCode(statusCode)
                .price(price)
                .build();
    }
}
