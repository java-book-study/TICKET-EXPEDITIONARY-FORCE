package com.ticket.captain.ticket;

import com.ticket.captain.order.Order;
import com.ticket.captain.order.StatusCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ticket {
    @Id
    @GeneratedValue
    private Long ticketId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_no")
    private Order order;

    @Column(nullable = false)
    private String ticketNo;

    @Column(nullable = false)
    private Long festivalId;

    @Column(nullable = false)
    private Long festivalSq;

    @Enumerated(EnumType.ORDINAL)
    private StatusCode statusCode;

    @Column(nullable = false)
    private Long price;

    @Builder
    private Ticket(Long ticketId, String ticketNo, Long festivalId, Long festivalSq, StatusCode statusCode, Long price) {

        this.ticketId = ticketId;
        this.ticketNo = ticketNo;
        this.festivalId = festivalId;
        this.festivalSq = festivalSq;
        this.statusCode = statusCode;
        this.price = price;
    }

    public void update(StatusCode statusCode) {
        this.statusCode = statusCode;
    }
}