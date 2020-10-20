package com.ticket.captain.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.transaction.annotation.Transactional;


public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);
    boolean existsByLoginId(String loginId);
    Account findByEmail(String email);
}
