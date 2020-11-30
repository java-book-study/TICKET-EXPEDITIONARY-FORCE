package com.ticket.captain.ticket.dto;

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
    private Long festivalDetailId;
    private String statusCode;
    private Long price;

    @Builder
    private TicketDto(Long ticketId, String ticketNo, Long festivalDetailId, Long festivalSq, String statusCode, Long price) {
        this.ticketId = ticketId;
        this.ticketNo = ticketNo;
        this.festivalDetailId = festivalDetailId;
        this.statusCode = statusCode;
        this.price = price;
    }

    public static TicketDto of(Ticket ticket) {
        return TicketDto.builder()
                .ticketId(ticket.getTicketId())
                .ticketNo(ticket.getTicketNo())
                .festivalDetailId(ticket.getFestivalDetail().getId())
                .statusCode(ticket.getStatusCode())
                .price(ticket.getPrice())
                .build();
    }
}