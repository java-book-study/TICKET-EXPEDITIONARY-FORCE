package com.ticket.captain.order;

import com.ticket.captain.order.dto.OrderDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    OrderDto findByOrderNo(String orderNo);

    long deleteByOrderNo(String orderNo);
}
