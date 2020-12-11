package com.ticket.captain.account.dto;

import com.ticket.captain.account.Account;
import com.ticket.captain.common.Address;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class AccountCreateDto {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Length(min = 8, max = 50)
    private String password;

    @NotBlank
    private String nickname;

    @NotBlank
    @Length(min = 2, max = 20)
    private String name;

    private Address address;

    @Builder
    private AccountCreateDto(String email, String password, String nickname, String name, Address address) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.address = address;
    }

    public Account toEntity(){
        return Account.builder()
                .email(email)
                .nickname(nickname)
                .name(name)
                .address(address)
                .build();
    }
}
