package com.ticket.captain.festival;

import com.ticket.captain.common.ErrorsResource;
import com.ticket.captain.exception.NotFoundException;
import com.ticket.captain.festival.dto.FestivalCreateDto;
import com.ticket.captain.festival.dto.FestivalDto;
import com.ticket.captain.festival.dto.FestivalUpdateDto;
import com.ticket.captain.festival.validator.FestivalCreateValidator;
import com.ticket.captain.response.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.MediaTypes;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/manager/festival", produces = MediaTypes.HAL_JSON_VALUE)
@Slf4j
public class FestivalManagerController {

    private final FestivalService festivalService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        FestivalCreateValidator validator = new FestivalCreateValidator(festivalService);
        binder.addValidators(validator);
    }

    @PostMapping("generate")
    public ApiResponseDto<FestivalDto> generate(@Valid @RequestBody FestivalCreateDto festivalCreateDto,
                                                Errors errors) {
        if (errors.hasErrors()) {
            throw new IllegalArgumentException("validator checking");
        }
        return ApiResponseDto.createOK(festivalService.add(festivalCreateDto));
    }

    /*
    FestivalUserController의 festivals 메서드와 동일함
     */
    @GetMapping("festivals")
    public ApiResponseDto<List<FestivalDto>> festivals(Pageable pageable) {
        return ApiResponseDto.createOK(festivalService.findAll(pageable));
    }

    /*
    FestivalUserController의 info 메서드와 동일함
     */
    @GetMapping("info/{festivalId}")
    public ApiResponseDto<FestivalDto> info(@PathVariable Long festivalId) {
        return ApiResponseDto.createOK(festivalService.findById(festivalId));
    }

    @PutMapping("update/{festivalId}")
    public ApiResponseDto<FestivalDto> update(@PathVariable Long festivalId,@RequestBody FestivalUpdateDto festivalUpdateDto) {
        return ApiResponseDto.createOK(festivalService.update(festivalId, festivalUpdateDto));
    }

    @DeleteMapping("delete/{festivalId}")
    public ApiResponseDto<String> delete(@PathVariable Long festivalId) {
        festivalService.delete(festivalId);
        return ApiResponseDto.DEFAULT_OK;
    }
}
