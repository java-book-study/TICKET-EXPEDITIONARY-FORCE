package com.ticket.captain.festival;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FestivalRestController {

    private final FestivalService festivalService;

    public FestivalRestController(FestivalService festivalService) {
        this.festivalService = festivalService;
    }

    @PostMapping("/manager/generate")
    public ResponseEntity<FestivalDto> generate(@RequestBody FestivalRequest request) {

        return ResponseEntity.ok(
            new FestivalDto(
                    festivalService.generate(
                            new Festival(request.getName(), request.getContent(), request.getWinners(),
                            request.getThumbnail(), request.getStartDate(), request.getEndDate())
                    ))
        );
    }

    @GetMapping("/manager/{festivalId}/info")
    public ResponseEntity<FestivalDto> festivalInfo(@PathVariable Long festivalId) {
        return ResponseEntity.ok(
                festivalService.findById(festivalId)
                .map(FestivalDto::new)
                .orElseThrow(RuntimeException::new)
        );
    }


    @GetMapping("manager/festivals")
    public ResponseEntity<List<Festival>> getFestivals() {
        PageRequest pageRequest = PageRequest.of(
                0, 12, Sort.by("createDate").descending());
        return ResponseEntity.ok(
                festivalService.getFestivals(pageRequest)
        );
    }

}
