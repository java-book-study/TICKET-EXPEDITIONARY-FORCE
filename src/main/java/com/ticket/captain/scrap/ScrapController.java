package com.ticket.captain.scrap;

import com.ticket.captain.account.Account;
import com.ticket.captain.account.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScrapController {

    private final ScrapService scrapService;

    @RequestMapping(value = "/scrap/{festivalId}", method = RequestMethod.POST)
    public ResponseEntity<?> createScrap(@CurrentUser Account account, @PathVariable Long festivalId){

        Long scrapId = scrapService.createScrap(account, festivalId);

        return ResponseEntity.ok(scrapId);
    }

    @RequestMapping(value = "/scrap/{scrapId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteScrap(@CurrentUser Account account, @PathVariable Long scrapId){

        scrapService.deleteScrap(account, scrapId);

        return ResponseEntity.ok().build();
    }

}
