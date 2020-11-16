package com.ticket.captain.salesType.validator;

import com.ticket.captain.salesType.SalesTypeService;
import com.ticket.captain.salesType.dto.SalesTypeCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class SalesTypeValidator implements Validator {

    private final SalesTypeService salesTypeService;


    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(SalesTypeCreateDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SalesTypeCreateDto salesTypeCreateDto = (SalesTypeCreateDto) target;

        if (salesTypeService.exitstsByName(salesTypeCreateDto.getName())) {
            errors.rejectValue("salesTypeName", "invalid.salesTypeName", new Object[] {salesTypeCreateDto.getName()}, "이미 존재하는 이름입니다.");
        }
    }
}
