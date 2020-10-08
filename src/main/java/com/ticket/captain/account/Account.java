package com.ticket.captain.account;

import com.ticket.captain.account.dto.AccountUpdateRequestDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Column(name="account_id")
    @Id
    @GeneratedValue
    private Long id;

    private String email;

    private String password;

    private String name;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    @Embedded
    private Address address;

    public void update(AccountUpdateRequestDto requestDto) {
        this.email = requestDto.getEmail();
        this.name = requestDto.getName();
        this.role = requestDto.getRole();
    }

}
