package com.ticket.captain.account.dto;

import com.ticket.captain.account.Account;
import com.ticket.captain.account.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 은성님 dto 코드
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountResponseDto {

    private String email;
    private String name;
    private Role role;

    public AccountResponseDto(Account account){
        name = account.getName();
        email = account.getEmail();
//        role = account.getRole();
    }
}