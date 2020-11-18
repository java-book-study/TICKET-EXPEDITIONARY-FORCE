package com.ticket.captain.festivalCategory.validator;

import com.ticket.captain.festivalCategory.FestivalCategoryService;
import com.ticket.captain.festivalCategory.dto.FestivalCategoryCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class FestivalCategoryValidator implements Validator {

    private final FestivalCategoryService festivalCategoryService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(FestivalCategoryCreateDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {

        FestivalCategoryCreateDto festivalCategoryCreateDto = (FestivalCategoryCreateDto) target;

        if (festivalCategoryService.findByCategoryName(festivalCategoryCreateDto.getCategoryName()) != null) {
            errors.rejectValue("CategoryName", "invalid.CategoryName", new Object[]{festivalCategoryCreateDto.getCategoryName()}, "이미 존재하는 카테고리입니다.");
        }
    }
}
