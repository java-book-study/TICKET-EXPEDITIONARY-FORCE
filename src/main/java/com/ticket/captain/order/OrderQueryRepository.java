package com.ticket.captain.order;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ticket.captain.account.QAccount;
import com.ticket.captain.festival.QFestival;
import com.ticket.captain.festivalDetail.QFestivalDetail;
import com.ticket.captain.order.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class OrderQueryRepository {
    private final JPAQueryFactory queryFactory;

    QOrder order = QOrder.order;
    QAccount account = QAccount.account;
    QFestival festival = QFestival.festival;
    QFestivalDetail festivalDetail = QFestivalDetail.festivalDetail;

    public Page<OrderDto> findByAccountId(Pageable pageable, String accountEmail) {
        QueryResults<Order> orders = queryFactory.selectFrom(order)
                .join(order.account, account).fetchJoin()
                .join(order.festival, festival).fetchJoin()
                .join(order.festivalDetail, festivalDetail).fetchJoin()
                .where(order.account.email.eq(accountEmail))
                .orderBy(order.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<OrderDto> parsedOrders = orders.getResults().stream()
                .map(OrderDto::of).collect(Collectors.toList());

        return new PageImpl<>(parsedOrders, pageable, orders.getTotal());
    }

    public Page<OrderDto> findByAccountWithDate(Pageable pageable, String accountEmail, LocalDate startDate, LocalDate endDate) {
        QueryResults<Order> orders = queryFactory.selectFrom(order)
                .join(order.account, account).fetchJoin()
                .join(order.festival, festival).fetchJoin()
                .join(order.festivalDetail, festivalDetail).fetchJoin()
                .where(order.account.email.eq(accountEmail),
                        order.createDate.between(startDate.atStartOfDay(), LocalDateTime.of(endDate, LocalTime.MAX).withNano(0)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<OrderDto> parsedOrders = orders.getResults().stream()
                .map(OrderDto::of).collect(Collectors.toList());

        return new PageImpl<>(parsedOrders, pageable, orders.getTotal());
    }
}
