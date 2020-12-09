package com.ticket.captain.scrap;


import com.ticket.captain.account.Account;
import com.ticket.captain.festivalDetail.FestivalDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    Long deleteByAccountAndFestivalDetail(Account account, FestivalDetail festivalDetail);
}
