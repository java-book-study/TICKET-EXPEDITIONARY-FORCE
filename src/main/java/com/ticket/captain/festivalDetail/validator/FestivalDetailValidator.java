package com.ticket.captain.festivalDetail.validator;

import com.ticket.captain.festivalDetail.FestivalDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class FestivalDetailValidator implements Validator {

    private final FestivalDetailService festivalDetailService;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
