package com.ticket.captain.account.dto;


import com.ticket.captain.account.Account;
import com.ticket.captain.account.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AccountDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {

        private Long id;

        private String name;
        private String email;
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

        private String name;
        private String email;
        private Role role;
    }

}
