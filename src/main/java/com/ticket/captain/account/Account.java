package com.ticket.captain.account;

import com.ticket.captain.account.dto.AccountDto;
import com.ticket.captain.common.Address;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder @Getter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder.Default
    private int point = 0;
    @Embedded
    private Address address;

    //해당 부분 AccountStatus, Role 중복인데 이거 선택해야될듯
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_USER;

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

    public void update(AccountDto.Update updateRequestDto){
        this.name = updateRequestDto.getName();
        this.email = updateRequestDto.getEmail();
    }

    public void addRole(Role role){
        this.role=role;
    }
}
