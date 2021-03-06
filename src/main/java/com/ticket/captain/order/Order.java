package com.ticket.captain.order;

import com.ticket.captain.account.Account;
import com.ticket.captain.common.BaseEntity;
import com.ticket.captain.enumType.StatusCode;
import com.ticket.captain.festival.Festival;
import com.ticket.captain.festivalDetail.FestivalDetail;
import com.ticket.captain.ticket.Ticket;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    private String orderNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festival_id")
    private Festival festival;

    //festivalDetail 를 통해서 festivalId 받아서 필드 값으로 집어넣어 주어야 한다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festival_detail_id")
    private FestivalDetail festivalDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "status_id")
    private StatusCode statusCode;

    @CreatedDate
    private LocalDateTime purchaseDate;

    @Builder
    private Order(String orderNo, Festival festival, FestivalDetail festivalDetail,
                  Account account, StatusCode statusCode) {
        this.orderNo = orderNo;
        this.festival = festival;
        this.festivalDetail = festivalDetail;
        this.account = account;
        this.statusCode = statusCode;
    }

    /**
     * 할인 관한 interface 추가가 되어야함
     */
    //private Discount discountRate
    public static Order createOrder(String orderNo, Festival festival, FestivalDetail festivalDetail,
                                    Account account, StatusCode statusCode) {
        return Order.builder()
                .orderNo(orderNo)
                .festival(festival)
                .festivalDetail(festivalDetail)
                .account(account)
                .statusCode(statusCode)
                .build();
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }
}
