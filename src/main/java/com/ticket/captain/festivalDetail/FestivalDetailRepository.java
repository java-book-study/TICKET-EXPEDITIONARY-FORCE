package com.ticket.captain.festivalDetail;


import com.ticket.captain.scrap.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FestivalDetailRepository extends JpaRepository<FestivalDetail, Long> {

    FestivalDetail findByScrap(Scrap scrap);
}
