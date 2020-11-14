package com.ticket.captain.account.dto;


import com.ticket.captain.account.Account;
import com.ticket.captain.account.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class AccountDto {

        @NotBlank
        private Long id;
        @NotBlank
        @Length(min=2, max=20)
        private String name;

        @Email
        @NotBlank
        private String email;
        @NotBlank
        private Role role;

    public AccountDto(Account account) {
        this.id = account.getId();
        this.name = account.getName();
        this.email = account.getEmail();
        this.role = account.getRole();
    }
}
