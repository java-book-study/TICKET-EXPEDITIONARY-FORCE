package com.ticket.captain.order;

import com.ticket.captain.account.Account;
import com.ticket.captain.festivalDetail.FestivalDetail;
import com.ticket.captain.ticket.Ticket;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "OrderTable")
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_no")  //erdcloud 에 order_no 라고 돼있어서 일단 이렇게 했습니다.
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festival_sq")
    private FestivalDetail festivalDetail;

    //festivalDetail 를 통해서 festivalId 받아서 필드 값으로 집어넣어 주어야 한다.
    private Long festivalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "order")
    private List<Ticket> tickets = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private StatusCode statusCode;

    @CreatedDate
    private LocalDateTime purchaseDate;

    private int totalAmount;

    private int usePointAmount;

    /**
     * 할인 관한 interface 추가가 되어야함
     */
    //private Discount discountRate

}
