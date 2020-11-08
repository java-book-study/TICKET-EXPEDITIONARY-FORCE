package com.ticket.captain.account.dto;


import com.ticket.captain.account.Account;
import com.ticket.captain.account.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

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

    @NoArgsConstructor
    @Getter
    public static class Create{

        @Email
        @NotBlank
        private String email;

        @NotBlank
        @Length(min=8, max=50)
        private String password;

        @NotBlank
        private String nickname;

        @NotBlank
        private String name;


        @Builder
        public Create(String email, String password, String nickname, String name) {
            this.email = email;
            this.password = password;
            this.nickname = nickname;
            this.name = name;
        }

        public Account toEntity(){
            return Account.builder()
                    .email(email)
                    .nickname(nickname)
                    .name(name)
                    .createDate(LocalDateTime.now())
                    .build();
        }

        private String getIpInfo(){
            HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            String ip = req.getHeader("X-FORWARDED-FOR");
            if(ip == null){
                ip = req.getRemoteAddr();
            }
            return ip;
        }
    }

}
