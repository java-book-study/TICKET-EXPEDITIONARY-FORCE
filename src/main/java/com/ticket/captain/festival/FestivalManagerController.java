package com.ticket.captain.festival;

import com.ticket.captain.exception.ExceptionDto;
import com.ticket.captain.festival.dto.FestivalCreateDto;
import com.ticket.captain.festival.dto.FestivalDto;
import com.ticket.captain.festival.dto.FestivalUpdateDto;
import com.ticket.captain.festival.validator.FestivalCreateValidator;
import com.ticket.captain.response.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/manager/festival")
@Slf4j
public class FestivalManagerController {

    private final FestivalService festivalService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        FestivalCreateValidator validator = new FestivalCreateValidator(festivalService);
        binder.addValidators(validator);
    }

    @PostMapping
    public ApiResponseDto generate(@Valid @RequestBody FestivalCreateDto festivalCreateDto, Errors errors) {
        if (errors.hasErrors()) {
            String field = errors.getFieldError().getDefaultMessage();
            ExceptionDto exceptionDto = ExceptionDto.builder().message(field).build();
            return ApiResponseDto.VALIDATION_ERROR(exceptionDto);
        }

        return ApiResponseDto.createOK(festivalService.add(festivalCreateDto));
    }

    /*
    FestivalUserController의 festivals 메서드와 동일함
     */
    @GetMapping
    public ApiResponseDto<List<FestivalDto>> festivals(Pageable pageable) {
        return ApiResponseDto.createOK(festivalService.findAll(pageable));
    }

    /*
    FestivalUserController의 info 메서드와 동일함
     */
    @GetMapping("/{festivalId}")
    public ApiResponseDto<FestivalDto> info(@PathVariable Long festivalId) {
        return ApiResponseDto.createOK(festivalService.findById(festivalId));
    }

    @PutMapping("/{festivalId}")
    public ApiResponseDto update(@PathVariable Long festivalId,
                                 @Valid @RequestBody FestivalUpdateDto festivalUpdateDto,
                                 Errors errors) {
        if (errors.hasErrors()) {
            String field = errors.getFieldError().getDefaultMessage();
            ExceptionDto exceptionDto = ExceptionDto.builder().message(field).build();
            return ApiResponseDto.VALIDATION_ERROR(exceptionDto);
        }
        return ApiResponseDto.createOK(festivalService.update(festivalId, festivalUpdateDto));
    }

    @DeleteMapping("/{festivalId}")
    public ApiResponseDto<String> delete(@PathVariable Long festivalId) {
        festivalService.delete(festivalId);
        return ApiResponseDto.DEFAULT_OK;
    }

    @DeleteMapping("/deleteAll")
    public ApiResponseDto<String> deleteAll() {
        festivalService.deleteAll();
        return ApiResponseDto.DEFAULT_OK;
    }
}
