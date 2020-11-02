package com.ticket.captain.account.dto;


import com.ticket.captain.account.Account;
import com.ticket.captain.account.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class AccountDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {

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

        public Response(Account account){
            this.id = account.getId();
            this.name = account.getName();
            this.email = account.getEmail();
            this.role = account.getRole();
        }
    }
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Update{

        @Length(min=2, max=20)
        private String name;
        @Email
        private String email;

        private Role role;
    }

}
