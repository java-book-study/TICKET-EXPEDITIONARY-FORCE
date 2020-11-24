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

    private String statusCode;

    @Column(nullable = false)
    private Long price;

    @Builder
    private Ticket(String ticketNo, String statusCode, Long price) {
        this.ticketNo = ticketNo;
        this.statusCode = statusCode;
        this.price = price;
    }

    public void update(String statusCode) {
        this.statusCode = statusCode;
    }

    public void orderSetting(Order order) {
        this.order = order;
    }
}