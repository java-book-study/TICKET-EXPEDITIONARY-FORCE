package com.ticket.captain.ticket.dto;

import com.ticket.captain.ticket.Ticket;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketDto {
    private Long id;
    private String title;
    private Integer amount;

    @Builder
    private TicketDto(Long id, String title, Integer amount) {
        this.id = id;
        this.title = title;
        this.amount = amount;
    }

    public static TicketDto of(Ticket ticket) {
        return TicketDto.builder()
                .id(ticket.getId())
                .title(ticket.getTitle())
                .amount(ticket.getAmount())
                .build();
    }
}
