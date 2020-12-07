package com.ticket.captain.ticket.dto;

import com.ticket.captain.enumType.StatusCode;
import com.ticket.captain.ticket.Ticket;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketUpdateDto {
    private StatusCode statusCode;

    @Builder
    private TicketUpdateDto(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public TicketUpdateDto toDto() {
        return TicketUpdateDto.builder()
                .statusCode(statusCode)
                .build();
    }

    public void apply(Ticket ticket) {
        ticket.update(ticket.getStatusCode());
    }
}