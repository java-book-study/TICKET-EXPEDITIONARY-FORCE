package com.ticket.captain.order.dto;

import com.ticket.captain.account.Account;
import com.ticket.captain.festival.Festival;
import com.ticket.captain.festivalDetail.FestivalDetail;
import com.ticket.captain.order.Order;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDto {

    private String orderNo;

    private Festival festival;

    private FestivalDetail festivalDetail;

    private Account account;

    private String statusCode;

    @Builder
    private OrderDto(String orderNo, Festival festival, FestivalDetail festivalDetail, Account account, String statusCode) {
        this.orderNo = orderNo;
        this.festival = festival;
        this.festivalDetail = festivalDetail;
        this.account = account;
        this.statusCode = statusCode;
    }

    public static OrderDto of(Order order) {
        return OrderDto.builder()
                .orderNo(order.getOrderNo())
                .festival(order.getFestival())
                .festivalDetail(order.getFestivalDetail())
                .account(order.getAccount())
                .statusCode(order.getStatusCode().name())
                .build();
    }

}
