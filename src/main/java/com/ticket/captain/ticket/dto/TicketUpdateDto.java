package com.ticket.captain.ticket.dto;

import com.ticket.captain.ticket.Ticket;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketUpdateDto {
    private Integer amount;

    @Builder
    private TicketUpdateDto(Integer amount) {
        this.amount = amount;
    }

    public TicketUpdateDto toDto() {
        return TicketUpdateDto.builder()
                .amount(amount)
                .build();
    }

    public void apply(Ticket ticket) {
        ticket.update(amount);
    }
}
