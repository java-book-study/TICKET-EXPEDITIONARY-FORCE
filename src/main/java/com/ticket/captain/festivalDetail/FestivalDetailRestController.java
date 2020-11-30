package com.ticket.captain.festivalDetail;

import com.ticket.captain.festivalDetail.dto.FestivalDetailCreateDto;
import com.ticket.captain.festivalDetail.dto.FestivalDetailDto;
import com.ticket.captain.festivalDetail.dto.FestivalDetailUpdateDto;
import com.ticket.captain.response.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/manager/festivalDetail")
public class FestivalDetailRestController {

    private final FestivalDetailService festivalDetailService;

    @PostMapping("generate")
    public ApiResponseDto<FestivalDetailDto> generate(FestivalDetailCreateDto festivalDetailCreateDto) {
        return ApiResponseDto.createOK(festivalDetailService.add(festivalDetailCreateDto));
    }

    @GetMapping("festivalDetails")
    public ApiResponseDto<List<FestivalDetailDto>> festivalDetails(Pageable pageable) {
        return ApiResponseDto.createOK(festivalDetailService.findAll(pageable));
    }

    @GetMapping("info/{festivalDetailId}")
    public ApiResponseDto<FestivalDetailDto> info(@PathVariable Long festivalDetailId) {
        return ApiResponseDto.createOK(festivalDetailService.findById(festivalDetailId));
    }

    @PutMapping("update/{festivalDetailId}")
    public ApiResponseDto<FestivalDetailDto> update(@PathVariable Long festivalDetailId, FestivalDetailUpdateDto festivalDetailUpdateDto) {
        return ApiResponseDto.createOK(festivalDetailService.update(festivalDetailId, festivalDetailUpdateDto));
    }

    @DeleteMapping("delete/{festivalDetailId}")
    public ApiResponseDto<String> delete(@PathVariable Long festivalDetailId) {
        festivalDetailService.delete(festivalDetailId);

        return ApiResponseDto.DEFAULT_OK;
    }
}
