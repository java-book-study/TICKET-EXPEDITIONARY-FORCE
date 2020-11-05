package com.ticket.captain.ticket.dto;

import com.ticket.captain.ticket.Ticket;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketUpdateDto {
    private Long stateCode;

    @Builder
    private TicketUpdateDto(Long stateCode) {
        this.stateCode = stateCode;
    }

    public TicketUpdateDto toDto() {
        return TicketUpdateDto.builder()
                .stateCode(stateCode)
                .build();
    }

    public void apply(Ticket ticket) {
        ticket.update(stateCode);
    }
}
