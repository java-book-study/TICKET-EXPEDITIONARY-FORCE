package com.ticket.captain.festival;

import com.ticket.captain.festival.dto.FestivalResponseDto;
import com.ticket.captain.festival.dto.FestivalCreateDto;
import com.ticket.captain.festival.validator.FestivalCreateValidator;
import com.ticket.captain.response.ApiResponseDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/festival")
public class FestivalRestController {

    private final FestivalService festivalService;

    public FestivalRestController(FestivalService festivalService) {
        this.festivalService = festivalService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        FestivalCreateValidator validator=new FestivalCreateValidator(festivalService);
        binder.addValidators(validator);
    }

    @PostMapping("generate")
    public ApiResponseDto<FestivalResponseDto> generate(@RequestBody @Valid FestivalCreateDto request) {

        return ApiResponseDto.createOK(
                new FestivalResponseDto(
                festivalService.generate(request.newFestival())));
    }

    @GetMapping("{festivalId}/info")
    public ApiResponseDto<FestivalResponseDto> festivalInfo(@PathVariable Long festivalId) {
        return ApiResponseDto.createOK(
                festivalService.findById(festivalId)
                .map(FestivalResponseDto::new)
                .orElseThrow(RuntimeException::new)
        );
    }


    @GetMapping("festivals")
    public ApiResponseDto<List<FestivalResponseDto>> getFestivals(int page, int size) {
        PageRequest pageRequest = PageRequest.of(
                page, size, Sort.by("createDate").descending());
        return ApiResponseDto.createOK(
                festivalService.getFestivals(pageRequest).stream()
                        .map(FestivalResponseDto::new)
                        .collect(Collectors.toList())
        );
    }

    @PutMapping("{festivalId}/update")
    public ApiResponseDto<FestivalResponseDto> updateFestival(@PathVariable Long festivalId, FestivalCreateDto festivalCreateDto) {
        Festival festival = festivalService.updateFestival(festivalId, festivalCreateDto);
        return ApiResponseDto.createOK(
                new FestivalResponseDto(festival));
    }

    @DeleteMapping("{festivalId}/del")
    public ResponseEntity<Void> delFestival (@PathVariable Long festivalId) {
        festivalService.deleteFestival(festivalId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}
