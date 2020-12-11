package com.ticket.captain.scrap;

import com.ticket.captain.account.Account;
import com.ticket.captain.account.AccountRepository;
import com.ticket.captain.exception.NotFoundException;
import com.ticket.captain.festivalDetail.FestivalDetail;
import com.ticket.captain.festivalDetail.FestivalDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FestivalDetailScrapService {

    private final FestivalDetailRepository festivalDetailRepository;

    private final AccountRepository accountRepository;

    public List<FestivalDetail> viewFestivalDetailByScrap(Long accountId){

        Account account = accountRepository.findById(accountId).orElseThrow(NotFoundException::new);
        List<FestivalDetail> festivalDetails= new ArrayList<>();

        if(account.getScrap() == null ){
            throw new NotFoundException();
        }else{
            for(Scrap scrap : account.getScrap()){
                FestivalDetail byScrap = festivalDetailRepository.findByScrap(scrap);
                festivalDetails.add(byScrap);
            }
        }

        return festivalDetails;
    }
}
