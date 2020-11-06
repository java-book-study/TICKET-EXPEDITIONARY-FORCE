package com.ticket.captain.ticket;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ticket {
    @Id
    @GeneratedValue
    private Long ticketId;

    @Column(nullable = false)
    private String ticketNo;

    @Column(nullable = false)
    private String orderNo;

    @Column(nullable = false)
    private Long festivalId;

    @Column(nullable = false)
    private Long festivalSq;

    @Column(nullable = false)
    private Long statusCode;

    @Column(nullable = false)
    private Long price;

    @Builder
    private Ticket(Long ticketId, String ticketNo, String orderNo, Long festivalId, Long festivalSq, Long statusCode, Long price) {
        this.ticketId = ticketId;
        this.ticketNo = ticketNo;
        this.orderNo = orderNo;
        this.festivalId = festivalId;
        this.festivalSq = festivalSq;
        this.statusCode = statusCode;
        this.price = price;
    }

    public void update(Long statusCode) {
        this.statusCode = statusCode;
    }
}