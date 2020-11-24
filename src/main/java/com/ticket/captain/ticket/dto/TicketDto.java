package com.ticket.captain.ticket.dto;

import com.ticket.captain.order.StatusCode;
import com.ticket.captain.ticket.Ticket;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketDto {
    private Long ticketId;
    private String ticketNo;
    private Long festivalId;
    private Long festivalSq;
    private String statusCode;
    private Long price;

    @Builder
    private TicketDto(Long ticketId, String ticketNo, Long festivalId, Long festivalSq, String statusCode, Long price) {
        this.ticketId = ticketId;
        this.ticketNo = ticketNo;
        this.festivalId = festivalId;
        this.festivalSq = festivalSq;
        this.statusCode = statusCode;
        this.price = price;
    }

    public static TicketDto of(Ticket ticket) {
        return TicketDto.builder()
                .ticketId(ticket.getTicketId())
                .ticketNo(ticket.getTicketNo())
                .statusCode(ticket.getStatusCode())
                .price(ticket.getPrice())
                .build();
    }
}
