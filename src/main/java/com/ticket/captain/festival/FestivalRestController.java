package com.ticket.captain.festival;

import com.ticket.captain.response.ApiResponseDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class FestivalRestController {

    private final FestivalService festivalService;

    public FestivalRestController(FestivalService festivalService) {
        this.festivalService = festivalService;
    }

    @PostMapping("/manager/generate")
    public ApiResponseDto<FestivalDto> generate(@RequestBody FestivalRequest request) {

        return ApiResponseDto.OK(
                new FestivalDto(
                festivalService.generate(request.newFestival())));
    }

    @GetMapping("/manager/{festivalId}/info")
    public ApiResponseDto<FestivalDto> festivalInfo(@PathVariable Long festivalId) {
        return ApiResponseDto.OK(
                festivalService.findById(festivalId)
                .map(FestivalDto::new)
                .orElseThrow(RuntimeException::new)
        );
    }


    @GetMapping("manager/festivals")
    public ApiResponseDto<List<FestivalDto>> getFestivals(int page, int size) {
        PageRequest pageRequest = PageRequest.of(
                page, size, Sort.by("createDate").descending());
        return ApiResponseDto.OK(
                festivalService.getFestivals(pageRequest).stream()
                        .map(FestivalDto::new)
                        .collect(Collectors.toList())
        );
    }

    @PutMapping("manager/{festivalId}/update")
    public ApiResponseDto<FestivalDto> updateFestival(@PathVariable Long festivalId, FestivalRequest festivalRequest) {
        Festival festival = festivalService.updateFestival(festivalId, festivalRequest);
        return ApiResponseDto.OK(
                new FestivalDto(festival));
    }

    @DeleteMapping("manager/{festivalId}/del")
    public ResponseEntity<Void> delFestival (@PathVariable Long festivalId) {
        festivalService.deleteFestival(festivalId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}
