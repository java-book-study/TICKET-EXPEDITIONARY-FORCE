package com.ticket.captain.scrap;

import com.ticket.captain.account.Account;
import com.ticket.captain.exception.NotFoundException;
import com.ticket.captain.festival.Festival;
import com.ticket.captain.festival.FestivalRepository;
import com.ticket.captain.scrap.dto.ScrapDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScrapService {

    private final ScrapRepository scrapRepository;

    private final FestivalRepository festivalRepository;

    public ScrapDto createScrap(Account account , Long festivalId){

        Festival festival = festivalRepository.findById(festivalId).orElseThrow(NotFoundException::new);

        Scrap scrap = new Scrap(account,festival);

        return ScrapDto.of(scrapRepository.save(scrap));
    }

    public void deleteScrap(Account account, Long scrapId) {

        scrapRepository.deleteById(scrapId);

    }
}
