package com.ticket.captain.scrap;

import com.ticket.captain.account.Account;
import com.ticket.captain.festivalDetail.FestivalDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Scrap {

    @Id @GeneratedValue
    @Column(name = "scrap_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "festival_id")
    private Account account;

    @OneToOne
    private FestivalDetail festivalDetail;

}
