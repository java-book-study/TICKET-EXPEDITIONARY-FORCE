package com.ticket.captain.account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    boolean existsByLoginId(String loginId);
}
