package com.ticket.captain.festival;

import com.ticket.captain.festival.dto.FestivalDto;
import com.ticket.captain.response.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account/festival")
public class FestivalUserController {

    private final FestivalService festivalService;

    @GetMapping("festivals")
    public ApiResponseDto<List<FestivalDto>> festivals(int offset, int limit) {
        return ApiResponseDto.createOK(festivalService.findAll(offset, limit));
    }

    @GetMapping("info/{festivalId}")
    public ApiResponseDto<FestivalDto> info(@PathVariable Long festivalId) {
        return ApiResponseDto.createOK(festivalService.findById(festivalId));
    }
}
