package com.ticket.captain.scrap;

import com.ticket.captain.account.Account;
import com.ticket.captain.account.CurrentUser;
import com.ticket.captain.scrap.dto.ScrapDto;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/scrap/", produces = MediaTypes.HAL_JSON_VALUE)
public class ScrapController {

    private final ScrapService scrapService;

    @RequestMapping(value = "{festivalId}", method = RequestMethod.POST)
    public ResponseEntity<?> createScrap(@CurrentUser Account account, @PathVariable Long festivalId){

        ScrapDto scrapDto = scrapService.createScrap(account, festivalId);
        EntityModel<ScrapDto> scrapModel = EntityModel.of(scrapDto).add(linkTo(ScrapController.class).slash(scrapDto.getScrapId()).withSelfRel());
        scrapModel.add(Link.of("/docs/index.html#get-scrapId").withRel("profile"));

        return ResponseEntity.ok(scrapModel);
    }

    @RequestMapping(value = "{scrapId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteScrap(@CurrentUser Account account, @PathVariable Long scrapId){

        scrapService.deleteScrap(account, scrapId);

        return ResponseEntity.ok().build();
    }

}
