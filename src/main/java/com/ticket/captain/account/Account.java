package com.ticket.captain.account;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    private String profileImage;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    private int point;

    private String nickname;

    private LocalDateTime emailCheckTokenGeneratedAt;

    private String emailCheckToken;

    private boolean emailVerified;

    @Embedded
    private Address address;

}
