package com.ticket.captain.ticket;

import com.ticket.captain.common.BaseEntity;
import com.ticket.captain.festivalDetail.FestivalDetail;
import com.ticket.captain.order.Order;
import com.ticket.captain.enumType.StatusCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ticket extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "ticket_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festival_detail_id")
    private FestivalDetail festivalDetail;

    @Column(nullable = false)
    private String ticketNo;

    @Column(name = "status_id")
    private String statusCode;

    @Column(nullable = false)
    private BigDecimal price;

    @Builder
    private Ticket(String ticketNo, String statusCode, BigDecimal price) {
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

    public void festivalDetailSetting(FestivalDetail festivalDetail) {
        this.festivalDetail = festivalDetail;
    }
}