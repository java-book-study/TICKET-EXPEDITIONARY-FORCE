package com.ticket.captain.account.dto;

import com.ticket.captain.account.Account;
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

@NoArgsConstructor
@Getter
public class AccountCreateDto {

    @NotBlank
    @Length(min=2, max=20)
    private String loginId;

    @NotBlank
    @Length(min=8, max=50)
    private String password;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String nickname;

    @NotBlank
    private String name;


    @Builder
    public AccountCreateDto(String loginId, String password, String email, String nickname, String name) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
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
