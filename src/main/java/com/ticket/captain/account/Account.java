package com.ticket.captain.account;

import com.ticket.captain.base.Address;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder @Getter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue
    private Long id;
    private String publicIp;
    private String loginId;
    private String password;
    private String name;
    private String nickname;
    private String email;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private LocalDateTime emailCheckTokenGeneratedAt;
    private String emailCheckToken;
    private boolean emailVerified;
    private int point;
    @Embedded
    Address homeAddress;

    @Enumerated(EnumType.STRING)
    @Transient
    @Builder.Default
    private AccountStatus accountStatus = AccountStatus.ROLE_USER;

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void generateEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
        this.emailCheckTokenGeneratedAt = LocalDateTime.now();
    }

    public boolean isValidToken(String token) {
        return this.emailCheckToken.equals(token);
    }

    public void completeSignUp() {
        this.emailVerified = true;
        this.createDate = LocalDateTime.now();
    }
}
