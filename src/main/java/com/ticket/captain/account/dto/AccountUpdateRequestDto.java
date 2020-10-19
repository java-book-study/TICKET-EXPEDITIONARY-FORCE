package com.ticket.captain.account.dto;

import com.ticket.captain.account.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 은성님 dto코드
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountUpdateRequestDto {

    @Email
    @NotBlank
    private String email;

    private String name;

    private Role role;
}