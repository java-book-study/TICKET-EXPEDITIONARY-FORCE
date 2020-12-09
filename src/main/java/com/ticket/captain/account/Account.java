package com.ticket.captain.account;

import com.ticket.captain.account.dto.AccountUpdateDto;
import com.ticket.captain.common.Address;
import com.ticket.captain.common.BaseEntity;
import com.ticket.captain.order.Order;
import com.ticket.captain.scrap.Scrap;
import lombok.*;

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
public class Account extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "account_id")
    private Long id;

    @OneToMany(mappedBy = "account")
    private List<Order> orders = new ArrayList<>();

    @Column(unique = true)
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

    @OneToMany
    private List<Scrap> scrap;

    public void setPassword(String password) {
        this.password = password;
    }

    public void generateEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
    }

    public boolean isValidToken(String token) {
        return this.emailCheckToken.equals(token);
    }

    public long update(AccountUpdateDto updateRequestDto){
        this.name = updateRequestDto.getName();
        this.email = updateRequestDto.getEmail();
        this.nickname = updateRequestDto.getNickname();

        return this.id;
    }

    public void addRole(Role role){
        this.role=role;
    }
}
