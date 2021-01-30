package com.ticket.captain.scrap;

import com.ticket.captain.account.Account;
import com.ticket.captain.festival.Festival;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Scrap {

    @Id @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "festival_id")
    private Festival festival;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    public Scrap(Account account, Festival festival) {
        this.account = account;
        this.festival = festival;
    }
}
