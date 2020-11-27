package com.ticket.captain.ticket.dto;

import com.ticket.captain.order.StatusCode;
import com.ticket.captain.ticket.Ticket;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketCreateDto {
    private String ticketNo;
    private Long festivalDetailId;
    private StatusCode statusCode;
    private Long price;

    @Builder
    private TicketCreateDto(String ticketNo, Long festivalDetailId, StatusCode statusCode, Long price) {
        this.ticketNo = ticketNo;
        this.festivalDetailId = festivalDetailId;
        this.statusCode = statusCode;
        this.price = price;
    }

    public TicketCreateDto toDto() {
        return TicketCreateDto.builder()
                .ticketNo(ticketNo)
                .festivalDetailId(festivalDetailId)
                .statusCode(statusCode)
                .price(price)
                .build();
    }

    public Ticket toEntity() {
        return Ticket.builder()
                .ticketNo(ticketNo)
//                .festivalDetail()
                .statusCode(statusCode.name())
                .price(price)
                .build();
    }
}
