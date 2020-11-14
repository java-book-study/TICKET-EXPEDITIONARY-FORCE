package com.ticket.captain.account.dto;

import com.ticket.captain.account.Account;
import com.ticket.captain.common.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
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

    public Account toEntity(){
        return Account.builder()
                .email(email)
                .nickname(nickname)
                .name(name)
                .address(address)
                .build();
    }
}
