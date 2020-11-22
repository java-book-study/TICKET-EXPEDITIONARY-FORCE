package com.ticket.captain.order;

import com.ticket.captain.account.Account;
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

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Order")
public class Order {
    @Id
    private String orderNo;

    private Long festivalId;

    //festivalDetail 를 통해서 festivalId 받아서 필드 값으로 집어넣어 주어야 한다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festival_sq")
    private FestivalDetail festivalDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "order")
    private List<Ticket> tickets;

    @Enumerated(EnumType.ORDINAL)
    private StatusCode statusCode;

    @CreatedDate
    private LocalDateTime purchaseDate;
    /**
     * 할인 관한 interface 추가가 되어야함
     */
    //private Discount discountRate

    @Builder
    private Order (String orderNo, Long festivalId, FestivalDetail festivalDetail,
                              Account account, StatusCode statusCode){
        this.orderNo = orderNo;
        this.festivalId = festivalId;
        this.festivalDetail = festivalDetail;
        this.account = account;
        this.statusCode = statusCode;
    }

}
