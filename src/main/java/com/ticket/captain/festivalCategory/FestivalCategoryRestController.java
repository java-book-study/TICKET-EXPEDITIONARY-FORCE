package com.ticket.captain.festivalCategory;

import com.ticket.captain.festivalCategory.dto.FestivalCategoryCreateDto;
import com.ticket.captain.festivalCategory.dto.FestivalCategoryDto;
import com.ticket.captain.festivalCategory.dto.FestivalCategoryUpdateDto;
import com.ticket.captain.festivalCategory.validator.FestivalCategoryValidator;
import com.ticket.captain.response.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/manager/festivalCategory")
public class FestivalCategoryRestController {

    private final FestivalCategoryService festivalCategoryService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        FestivalCategoryValidator validator = new FestivalCategoryValidator(festivalCategoryService);
        binder.addValidators(validator);
    }

    @PostMapping("generate")
    public ApiResponseDto<FestivalCategoryDto> generate(@Valid FestivalCategoryCreateDto festivalCategoryCreateDto) {
        return ApiResponseDto.createOK(festivalCategoryService.add(festivalCategoryCreateDto.toDto()));
    }

    @GetMapping("festivalCategories")
    public ApiResponseDto<List<FestivalCategoryDto>> festivalCategories(int offset, int limit) {
        return ApiResponseDto.createOK(
                festivalCategoryService.findAll(offset, limit));
    }

    @GetMapping("info/{festivalCategoryId}")
    public ApiResponseDto<FestivalCategoryDto> Info(@PathVariable Long festivalCategoryId) {
        return ApiResponseDto.createOK(festivalCategoryService.findById(festivalCategoryId));
    }

    @PutMapping("update/{festivalCategoryId}")
    public ApiResponseDto<FestivalCategoryDto> update(@PathVariable Long festivalCategoryId, FestivalCategoryUpdateDto festivalCategoryUpdateDto) {
        return ApiResponseDto.createOK(festivalCategoryService.update(festivalCategoryId, festivalCategoryUpdateDto));
    }

    @DeleteMapping("delete/{festivalCategoryId}")
    public ApiResponseDto<String> delete(@PathVariable Long festivalCategoryId) {
        festivalCategoryService.delete(festivalCategoryId);
        return ApiResponseDto.DEFAULT_OK;
    }
}
