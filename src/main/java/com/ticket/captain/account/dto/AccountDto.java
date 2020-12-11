package com.ticket.captain.account.dto;


import com.ticket.captain.account.Account;
import com.ticket.captain.account.Role;
import com.ticket.captain.common.Address;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AccountDto {

    private Long id;

    private String email;

    private String profileImage;

    private String name;

    private String nickname;

    private long point;

    private String emailCheckToken;

    private LocalDateTime emailCheckTokenGenDate;

    private Role role;

    private Address address;

    @Builder
    private AccountDto(Long id, String email, String profileImage, String name,
                       String nickname, long point, String emailCheckToken, LocalDateTime emailCheckTokenGenDate, Role role, Address address) {
        this.id = id;
        this.email = email;
        this.profileImage = profileImage;
        this.name = name;
        this.nickname = nickname;
        this.point = point;
        this.emailCheckToken = emailCheckToken;
        this.emailCheckTokenGenDate = emailCheckTokenGenDate;
        this.role = role;
        this.address = address;
    }

    public static AccountDto of(Account account) {

        return AccountDto.builder()
                .id(account.getId())
                .email(account.getEmail())
                .profileImage(account.getProfileImage())
                .name(account.getName())
                .nickname(account.getNickname())
                .point(account.getPoint())
                .role(account.getRole())
                .address(account.getAddress())
                .emailCheckToken(account.getEmailCheckToken())
                .emailCheckTokenGenDate(account.getEmailCheckTokenGenDate())
                .build();
    }
}
