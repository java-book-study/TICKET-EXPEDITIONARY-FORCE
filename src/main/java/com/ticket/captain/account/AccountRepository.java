package com.ticket.captain.account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);

    boolean existsByLoginId(String loginId);
}
