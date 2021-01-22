package com.ticket.captain.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ticket.captain.account.dto.AccountUpdateDto;
import com.ticket.captain.common.Address;
import com.ticket.captain.common.BaseEntity;
import com.ticket.captain.order.Order;
import com.ticket.captain.scrap.Scrap;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@AllArgsConstructor
public class Account extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "account_id")
    private Long id;

    @OneToMany(mappedBy = "account")
    private final List<Order> orders = new ArrayList<>();

    @Column(unique = true)
    @Email
    private String email;

    private String password;

    private String profileImage;

    private String name;

    private String nickname;

    @Builder.Default
    private long point = 0;

    @Embedded
    private Address address;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Role role;

    private String emailCheckToken;

    private LocalDateTime emailCheckTokenGenDate;

    @OneToMany(mappedBy = "account")
    private List<Scrap> scraps;

    @Builder
    public Account(String email, String password, String profileImage, String name,
                   String nickname, int point, Address address, Role role) {
        this.email = email;
        this.password = password;
        this.profileImage = profileImage;
        this.name = name;
        this.nickname = nickname;
        this.point = point;
        this.address = address;
        this.role = role;
    }

    public Account() {

    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void generateEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
        this.emailCheckTokenGenDate = LocalDateTime.now();
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

    public void completeSignUp() {
        this.role = Role.ROLE_USER;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("email", email)
                .append("password", password)
                .append("profileImage", profileImage)
                .append("name", name)
                .append("nickname", nickname)
                .append("point", point)
                .append("address", address)
                .append("role", role)
                .toString();
    }
}
