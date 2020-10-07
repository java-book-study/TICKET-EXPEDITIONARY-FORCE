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

    @Column(unique = true)
    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    @Embedded
    private Address address;

}
