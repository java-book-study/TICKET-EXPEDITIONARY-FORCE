package com.ticket.captain.order;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ticket.captain.account.QAccount;
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
    QAccount account = QAccount.account;

    public List<Order> findByAccountId(Pageable pageable, String accountEmail) {
        return queryFactory.selectFrom(order)
                .join(account).on(account.email.eq(accountEmail))
                .where(order.account.id.eq(account.id))
                .orderBy(order.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    public List<Order> findByAccountWithDate(Pageable pageable, String accountEmail, LocalDateTime startDate, LocalDateTime endDate) {
        return queryFactory.selectFrom(order)
                .join(account).on(account.email.eq(accountEmail))
                .where(order.account.id.eq(account.id),
                        order.createDate.between(startDate, endDate))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
