package com.ticket.captain.account;

import com.ticket.captain.account.dto.AccountDto;
import com.ticket.captain.common.Address;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    @Column(name = "account_id")
    private Long id;

    private String email;

    private String password;

    private String profile_image;

    private String name;

    private String nickname;

    @Builder.Default
    private int point = 0;

    @Embedded
    private Address address;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_USER;

    private String emailCheckToken;

    private LocalDateTime emailCheckTokenGenDate;

    private String createId;

    @CreationTimestamp
    private LocalDateTime createDate;

    @UpdateTimestamp
    private LocalDateTime modifyDate;

    private String modifyId;

    public void setPassword(String password) {
        this.password = password;
    }

    public void generateEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
    }

    public boolean isValidToken(String token) {
        return this.emailCheckToken.equals(token);
    }

    public void completeSignUp() {
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
