package com.ticket.captain.scrap;

import com.ticket.captain.account.Account;
import com.ticket.captain.festivalDetail.FestivalDetail;
import com.ticket.captain.main.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage/scraps")
public class FestivalDetailScrapApiController {

    private final FestivalDetailScrapService festivalDetailService;

    @GetMapping()
    public ResponseEntity viewFestivalDetailByScrap(@CurrentUser Account account){

        List<FestivalDetail> festivalDetail = festivalDetailService.viewFestivalDetailByScrap(account.getId());

        return ResponseEntity.ok(festivalDetail);

    }

}
