package com.ticket.captain.order;

import com.ticket.captain.account.Account;
import com.ticket.captain.festivalDetail.FestivalDetail;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "festival_sq")
    private FestivalDetail festivalDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account")
    private Account account;

    @Enumerated(EnumType.STRING)
    private StatusCode statusCode;

    private LocalDateTime purchaseDate;

    private int totalAmount;

    private int usePointAmount;

    private int discountRate;

}
