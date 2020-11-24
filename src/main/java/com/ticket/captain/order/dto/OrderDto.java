package com.ticket.captain.order.dto;

import com.ticket.captain.order.Order;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDto {

    private String orderNo;

    private Long festivalId;

    private Long festivalDetailId;

    private Long accountId;

    private String statusCode;

    @Builder
    private OrderDto(String orderNo, Long festivalId, Long festivalDetailId, Long accountId, String statusCode) {
        this.orderNo = orderNo;
        this.festivalId = festivalId;
        this.festivalDetailId = festivalDetailId;
        this.accountId = accountId;
        this.statusCode = statusCode;
    }

    public static OrderDto of(Order order) {
        return OrderDto.builder()
                .orderNo(order.getOrderNo())
                .festivalId(order.getFestivalId())
                .festivalDetailId(order.getFestivalDetail().getId())
                .accountId(order.getAccount().getId())
                .statusCode(order.getStatusCode())
                .build();
    }

}
