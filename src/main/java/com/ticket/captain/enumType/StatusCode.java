package com.ticket.captain.enumType;

public enum StatusCode {
    PURCHASE(2), READY(3), SHIPPING(4), DELIVERED(5), REFUND(8), PARTLY_CANCELD(9), CANCELED(10);
    Integer code;

    StatusCode(Integer code) {
        this.code = code;
    }

}
