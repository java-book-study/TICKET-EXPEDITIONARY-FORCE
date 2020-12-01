package com.ticket.captain.festivalDetail;

import com.ticket.captain.common.BaseEntity;
import com.ticket.captain.festival.Festival;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "festival_detail")
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FestivalDetail extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "festival_detail_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festival_id")
    private Festival festival;

    @Column(name = "sales_type")
    String salesType;

    @Column(name = "ticket_amount")
    private Long amount;

    @Column(name = "ticket_price")
    private Long price;

    @Column(name = "perform_datetime")
    private LocalDateTime processDate;

    @Column(name = "draw_date")
    private LocalDateTime drawDate;

    @Builder
    public FestivalDetail(String salesType,Long amount, Long price,
                          LocalDateTime processDate, LocalDateTime drawDate, Festival festival) {
        this.salesType = salesType;
        this.amount = amount;
        this.price = price;
        this.processDate = processDate;
        this.drawDate = drawDate;
        this.festival = festival;
    }


    public void update(String salesType,Long amount, Long price,
                       LocalDateTime processDate, LocalDateTime drawDate) {
        this.amount = amount;
        this.price = price;
        this.salesType = salesType;
        this.processDate = processDate;
        this.drawDate = drawDate;
    }
}