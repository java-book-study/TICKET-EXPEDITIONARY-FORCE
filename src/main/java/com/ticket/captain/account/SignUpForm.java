package com.ticket.captain.account;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class SignUpForm {
    @NotBlank
    private String loginId;

    @NotBlank
    @Length(min=8, max=50)
    private String password;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String nickname;

    public String getLoginId() {
        return loginId;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }
}
