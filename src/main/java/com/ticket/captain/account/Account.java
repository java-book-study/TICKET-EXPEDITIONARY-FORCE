package com.ticket.captain.account;

import com.ticket.captain.account.dto.AccountUpdateDto;
import com.ticket.captain.common.Address;
import com.ticket.captain.order.Order;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    @OneToMany(mappedBy = "account")
    private List<Order> orders = new ArrayList<>();

    private String email;

    private String password;

    private String profileImage;

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

    @CreationTimestamp
    private LocalDateTime createDate;

    @UpdateTimestamp
    private LocalDateTime modifyDate;

    private Long modifyId;

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

    public void update(AccountUpdateDto updateRequestDto){
        this.name = updateRequestDto.getName();
        this.email = updateRequestDto.getEmail();
    }

    public void addRole(Role role){
        this.role=role;
    }
}
