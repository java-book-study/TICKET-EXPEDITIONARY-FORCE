package com.ticket.captain.festival.validator;

import com.ticket.captain.festival.FestivalService;
import com.ticket.captain.festival.dto.FestivalCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class FestivalCreateValidator implements Validator {

    private final FestivalService festivalService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(FestivalCreateDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        FestivalCreateDto festivalCreateDto = (FestivalCreateDto) target;
        if(festivalService.existsByName(festivalCreateDto.getName())){
            errors.rejectValue("name", "invalid.name", new Object[]{festivalCreateDto.getName()}, "이미 사용중인 이름입니다.");
        }
    }
}
