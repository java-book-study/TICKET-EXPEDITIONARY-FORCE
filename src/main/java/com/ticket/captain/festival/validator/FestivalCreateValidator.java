package com.ticket.captain.festival.validator;

import com.ticket.captain.festival.FestivalService;
import com.ticket.captain.festival.dto.FestivalCreateDto;
import com.ticket.captain.festival.dto.FestivalUpdateDto;
import lombok.NoArgsConstructor;
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
        if (target instanceof FestivalCreateDto) {
            FestivalCreateDto festivalCreateDto = (FestivalCreateDto) target;
            if(festivalService.findByTitle(festivalCreateDto.getTitle()) != null){
                errors.rejectValue("title", "Invalid Title", new Object[]{festivalCreateDto.getTitle()},
                        "이미 사용중인 이름입니다.");
            }
        } else if (target instanceof FestivalUpdateDto) {
            FestivalUpdateDto festivalUpdateDto = (FestivalUpdateDto) target;
            if(festivalService.findByTitle(festivalUpdateDto.getTitle()) != null){
                errors.rejectValue("title", "Invalid Title", new Object[]{festivalUpdateDto.getTitle()},
                        "이미 사용중인 이름입니다.");
            }
        }
    }
}
