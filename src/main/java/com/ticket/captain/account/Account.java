package com.ticket.captain.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
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
    private String city;
    private String street;
    private String zipcode;

    @Enumerated(EnumType.STRING)
    @Transient
    @Builder.Default
    private AccountStatus accountStatus = AccountStatus.ROLE_USER;

    public Long getId() {
        return id;
    }

    public String getPublicIp() {
        return publicIp;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    public LocalDateTime getEmailCheckTokenGeneratedAt() {
        return emailCheckTokenGeneratedAt;
    }

    public String getEmailCheckToken() {
        return emailCheckToken;
    }

    public int getPoint() {
        return point;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

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
