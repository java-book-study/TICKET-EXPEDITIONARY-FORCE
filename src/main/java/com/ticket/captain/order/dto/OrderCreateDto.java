package com.ticket.captain.order.dto;

import com.ticket.captain.order.Order;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderCreateDto {

    private String orderNo;

    private Long festivalDetailId;

    private Long accountId;

    private String statusCode;

    @Builder
    private OrderCreateDto(String orderNo, Long festivalDetailId, Long accountId, String statusCode) {
        this.orderNo = orderNo;
        this.festivalDetailId = festivalDetailId;
        this.accountId = accountId;
        this.statusCode = statusCode;
    }

    public static OrderCreateDto of(Order order) {
        return OrderCreateDto.builder()
                .orderNo(order.getOrderNo())
                .festivalDetailId(order.getFestivalDetail().getId())
                .accountId(order.getAccount().getId())
                .statusCode(order.getStatusCode().name())
                .build();
    }

}
