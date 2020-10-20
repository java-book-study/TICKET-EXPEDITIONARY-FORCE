package com.ticket.captain.account;

public interface AccountRepositoryCustom {
    Account findAccount(String email, String password);
}
