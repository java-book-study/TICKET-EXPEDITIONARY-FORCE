package com.ticket.captain.ticket.dto;

import com.ticket.captain.enumType.StatusCode;
import com.ticket.captain.ticket.Ticket;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketUpdateDto {
    private String statusCode;

    @Builder
    private TicketUpdateDto(String statusCode) {
        this.statusCode = statusCode;
    }

    public TicketUpdateDto toDto() {
        return TicketUpdateDto.builder()
                .statusCode(statusCode)
                .build();
    }

    public void apply(Ticket ticket) {
        ticket.update(statusCode);
    }
}