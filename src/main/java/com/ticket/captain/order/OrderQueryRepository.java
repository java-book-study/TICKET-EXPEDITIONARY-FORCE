package com.ticket.captain.order;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class OrderQueryRepository {
    private final JPAQueryFactory queryFactory;

    QOrder order = QOrder.order;

    public List<Order> findByAccountId(Pageable pageable, Long accountId) {
        return queryFactory.selectFrom(order)
                .where(order.account.id.eq(accountId))
                .orderBy(order.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    public List<Order> findByAccountWithDate(Pageable pageable, Long accountId, LocalDateTime startDate, LocalDateTime endDate) {
        return queryFactory.selectFrom(order)
                .where(order.account.id.eq(accountId),
                        order.createDate.between(startDate, endDate))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
