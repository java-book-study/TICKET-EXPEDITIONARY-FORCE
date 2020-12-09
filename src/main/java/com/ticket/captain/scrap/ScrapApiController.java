package com.ticket.captain.scrap;

import com.ticket.captain.account.Account;
import com.ticket.captain.main.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/scrap")
public class ScrapApiController {

    private final ScrapService scrapService;

    @PutMapping("{id}")
    public ResponseEntity createScrap(@CurrentUser Account account, @PathVariable("id") Long festivalDetailId) {

        scrapService.createScrap(account.getId(), festivalDetailId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteScrap(@CurrentUser Account account, @PathVariable("id") Long festivalDetailId) {

        scrapService.deleteScrap(account.getId(), festivalDetailId);

        return ResponseEntity.ok().build();
    }

}
