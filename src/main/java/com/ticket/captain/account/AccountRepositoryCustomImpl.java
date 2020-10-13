package com.ticket.captain.account;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

import static com.ticket.captain.account.QAccount.account;

public class AccountRepositoryCustomImpl implements AccountRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public AccountRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Account findAccount(String email, String password) {
        return queryFactory
                .selectFrom(account)
                .where(
                        emailAndpwdEqual(email,password)
                )
                .fetchOne();
    }

    private BooleanBuilder emailAndpwdEqual(String email, String pwd) {
        BooleanBuilder bb = new BooleanBuilder();
        bb.and(emailEqual(email));
        bb.and(pwdEqual(pwd));
        return bb;
    }

    private BooleanExpression emailEqual(String email) {
        return account.email.eq(email);
    }

    private BooleanExpression pwdEqual(String pwd){
        return account.password.eq(pwd);
    }
}
