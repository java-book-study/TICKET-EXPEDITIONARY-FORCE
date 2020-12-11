package com.ticket.captain.scrap;

import com.ticket.captain.account.Account;
import com.ticket.captain.account.AccountRepository;
import com.ticket.captain.exception.NotFoundException;
import com.ticket.captain.festivalDetail.FestivalDetail;
import com.ticket.captain.festivalDetail.FestivalDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ScrapService {

    private final ScrapRepository scrapRepository;

    private final FestivalDetailRepository festivalDetailRepository;

    private final AccountRepository accountRepository;

    public void createScrap(Long accountId, Long festivalDetailId) {

        FestivalDetail festivalDetail = festivalDetailRepository.findById(festivalDetailId)
                .orElseThrow(NotFoundException::new);
        Account account = accountRepository.findById(accountId).orElseThrow(NotFoundException::new);

        scrapRepository.save(ScrapRequestDto.toEntity(account, festivalDetail));

    }

    public void deleteScrap(Long accountId, Long festivalDetailId){

        FestivalDetail festivalDetail = festivalDetailRepository.findById(festivalDetailId)
                .orElseThrow(NotFoundException::new);
        Account account = accountRepository.findById(accountId).orElseThrow(NotFoundException::new);

        Long scrapId = scrapRepository.deleteByAccountAndFestivalDetail(account, festivalDetail);
    }

}
