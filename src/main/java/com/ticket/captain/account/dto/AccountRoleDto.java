package com.ticket.captain.account.dto;

import com.ticket.captain.account.Account;
import com.ticket.captain.account.Role;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountRoleDto {

    private Long id;

    private Role role;

    public static AccountRoleDto of(Account account){
        return AccountRoleDto.builder()
                .id(account.getId())
                .role(account.getRole())
                .build();
    }
}
