package com.ticket.captain.account;

import javax.persistence.Column;
import java.time.LocalDateTime;

public class Account {

    @Column(name="account_id")
    private Long id;
    private String publicIp;
    private String accountLoginId;
    private String accountPassword;
    private String accountName;
    private String nickname;
    private String email;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPublicIp() {
        return publicIp;
    }

    public void setPublicIp(String publicIp) {
        this.publicIp = publicIp;
    }

    public String getAccountLoginId() {
        return accountLoginId;
    }

    public void setAccountLoginId(String accountLoginId) {
        this.accountLoginId = accountLoginId;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }
}
