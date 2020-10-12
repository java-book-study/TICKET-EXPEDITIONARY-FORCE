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
                        emailAndpwdEq(email,password)
                )
                .fetchOne();
    }

    private BooleanBuilder emailAndpwdEq(String email, String pwd) {
        BooleanBuilder bb = new BooleanBuilder();
        bb.and(emailEq(email));
        bb.and(pwdEq(pwd));
        return bb;
    }

    private BooleanExpression emailEq(String email) {
        return account.email.eq(email);
    }

    private BooleanExpression pwdEq(String pwd){
        return account.password.eq(pwd);
    }
}
