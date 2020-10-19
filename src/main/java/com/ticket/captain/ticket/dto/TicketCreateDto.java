package com.ticket.captain.ticket.dto;

import com.ticket.captain.ticket.Ticket;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketCreateDto {
    private String title;
    private Integer amount;

    @Builder
    private TicketCreateDto(String title, Integer amount) {
        this.title = title;
        this.amount = amount;
    }

    public TicketCreateDto toDto() {
        return TicketCreateDto.builder()
                .title(title)
                .amount(amount)
                .build();
    }

    public Ticket toEntity() {
        return Ticket.builder()
                .title(title)
                .amount(amount)
                .build();
    }
}
