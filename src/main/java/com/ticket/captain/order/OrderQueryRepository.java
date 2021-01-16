package com.ticket.captain.order;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ticket.captain.account.QAccount;
import com.ticket.captain.order.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class OrderQueryRepository {
    private final JPAQueryFactory queryFactory;

    QOrder order = QOrder.order;
    QAccount account = QAccount.account;

    public Page<OrderDto> findByAccountId(Pageable pageable, String accountEmail) {
        QueryResults<Order> orders = queryFactory.selectFrom(order)
                .join(account).on(account.email.eq(accountEmail))
                .where(order.account.id.eq(account.id))
                .orderBy(order.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<OrderDto> parsedOrders = orders.getResults().stream()
                .map(OrderDto::of).collect(Collectors.toList());

        return new PageImpl<>(parsedOrders, pageable, orders.getTotal());
    }

    public Page<OrderDto> findByAccountWithDate(Pageable pageable, String accountEmail, LocalDateTime startDate, LocalDateTime endDate) {
        QueryResults<Order> orders = queryFactory.selectFrom(order)
                .join(account).on(account.email.eq(accountEmail))
                .where(order.account.id.eq(account.id),
                        order.createDate.between(startDate, endDate))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<OrderDto> parsedOrders = orders.getResults().stream()
                .map(OrderDto::of).collect(Collectors.toList());

        return new PageImpl<>(parsedOrders, pageable, orders.getTotal());
    }
}
