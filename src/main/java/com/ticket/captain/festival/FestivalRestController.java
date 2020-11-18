package com.ticket.captain.festival;

import com.ticket.captain.exception.NotFoundException;
import com.ticket.captain.festival.dto.FestivalCreateDto;
import com.ticket.captain.festival.dto.FestivalDto;
import com.ticket.captain.festival.dto.FestivalUpdateDto;
import com.ticket.captain.festival.validator.FestivalCreateValidator;
import com.ticket.captain.festivalCategory.FestivalCategory;
import com.ticket.captain.festivalCategory.FestivalCategoryService;
import com.ticket.captain.response.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/manager/festival")
public class FestivalRestController {

    private final FestivalService festivalService;

    private final FestivalCategoryService festivalCategoryService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        FestivalCreateValidator validator = new FestivalCreateValidator(festivalService);
        binder.addValidators(validator);
    }

    @PostMapping("generate")
    public ApiResponseDto<FestivalDto> generate(@Valid FestivalCreateDto festivalCreateDto) {
        return ApiResponseDto.createOK(festivalService.add(festivalCreateDto));
    }

    @GetMapping("festivals")
    public ApiResponseDto<List<FestivalDto>> festivals(int offset, int limit) {
        return ApiResponseDto.createOK(festivalService.findAll(offset, limit));
    }

    @GetMapping("info/{festivalId}")
    public ApiResponseDto<FestivalDto> info(@PathVariable Long festivalId) {
        return ApiResponseDto.createOK(festivalService.findById(festivalId));
    }

    @PutMapping("update/{festivalId}")
    public ApiResponseDto<FestivalDto> update(@PathVariable Long festivalId, FestivalUpdateDto festivalUpdateDto) {
        return ApiResponseDto.createOK(festivalService.update(festivalId, festivalUpdateDto));
    }

    @DeleteMapping("delete/{festivalId}")
    public ApiResponseDto<String> delete(@PathVariable Long festivalId) {
        festivalService.delete(festivalId);

        return ApiResponseDto.DEFAULT_OK;
    }


    @PostMapping("category/add/{festivalId}")
    public ApiResponseDto<FestivalDto> addCategory(String categoryName, @PathVariable Long festivalId) {
        FestivalCategory category = festivalCategoryService.findByCategoryName(categoryName);
        if (category == null)  throw new NotFoundException();
        return ApiResponseDto.createOK(festivalService.addCategory(category, festivalId));
    }
}
