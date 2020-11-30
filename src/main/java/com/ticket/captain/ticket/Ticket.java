package com.ticket.captain.ticket;

import com.ticket.captain.festivalDetail.FestivalDetail;
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
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festival_detail_id")
    private FestivalDetail festivalDetail;

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

    public void update(StatusCode statusCode) {
        this.statusCode = statusCode.name();
    }

    public void orderSetting(Order order) {
        this.order = order;
    }
}