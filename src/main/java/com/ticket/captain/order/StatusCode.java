package com.ticket.captain.order;

public enum StatusCode {
    PURCHASE("결제"),READY("배송준비중"), SHIPPING("배송중"), DELIVERED("배송완료"), PARTLY_CANCELD("부분취소"),
    CANCELED("취소"), REFUND("반품");
    String code;
    StatusCode(String code){
        this.code = code;
    };
}
