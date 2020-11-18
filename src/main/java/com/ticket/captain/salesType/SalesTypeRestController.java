package com.ticket.captain.salesType;

import com.ticket.captain.response.ApiResponseDto;
import com.ticket.captain.salesType.dto.SalesTypeCreateDto;
import com.ticket.captain.salesType.dto.SalesTypeDto;
import com.ticket.captain.salesType.dto.SalesTypeUpdateDto;
import com.ticket.captain.salesType.validator.SalesTypeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/manager/salesType")
public class SalesTypeRestController {

    private final SalesTypeService salesTypeService;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SalesTypeValidator validator = new SalesTypeValidator(salesTypeService);
        webDataBinder.addValidators(validator);
    }

    @PostMapping("generate")
    public ApiResponseDto<SalesTypeDto> generate(SalesTypeCreateDto salesTypeCreateDto) {
        return ApiResponseDto.createOK(salesTypeService.add(salesTypeCreateDto.toDto()));
    }

    @GetMapping("salesTypifies")
    public ApiResponseDto<List<SalesTypeDto>> salesTypifies(int offset, int limit) {
        return ApiResponseDto.createOK(salesTypeService.findAll(offset, limit));
    }

    @GetMapping("info/{salesTypeId}")
    public ApiResponseDto<SalesTypeDto> info(@PathVariable Long salesTypeId) {
        return ApiResponseDto.createOK(salesTypeService.findById(salesTypeId));
    }

    @PutMapping("update/{salesTypeId}")
    public ApiResponseDto<SalesTypeDto> update(@PathVariable Long salesTypeId, SalesTypeUpdateDto salesTypeUpdateDto) {
        return ApiResponseDto.createOK(salesTypeService.update(salesTypeId, salesTypeUpdateDto));
    }

    @DeleteMapping("delete/{salesTypeId}")
    public ApiResponseDto<String> delete(@PathVariable Long salesTypeId) {
        salesTypeService.delete(salesTypeId);
        return ApiResponseDto.DEFAULT_OK;
    }
}
