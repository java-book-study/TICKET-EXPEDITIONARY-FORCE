package com.ticket.captain.salesType;

import com.ticket.captain.exception.NotFoundException;
import com.ticket.captain.salesType.SalesTypeRepository;
import com.ticket.captain.salesType.dto.SalesTypeCreateDto;
import com.ticket.captain.salesType.dto.SalesTypeDto;
import com.ticket.captain.salesType.dto.SalesTypeUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SalesTypeService {

    private final SalesTypeRepository salesTypeRepository;

    public SalesTypeDto add(SalesTypeCreateDto salesTypeCreateDto) {
        SalesType salesType = salesTypeCreateDto.toEntity();

        return SalesTypeDto.of(salesTypeRepository.save(salesType));
    }

    @Transactional(readOnly = true)
    public List<SalesTypeDto> findAll(int offset, int limit) {

        PageRequest pageRequest = PageRequest.of(offset, limit);

        return salesTypeRepository.findAll(pageRequest).stream()
                .map(SalesTypeDto::of)
                .collect(Collectors.toList());
    }

    public SalesTypeDto findById(Long salesTypeId) {

        return salesTypeRepository.findById(salesTypeId)
                .map(SalesTypeDto::of)
                .orElseThrow(NotFoundException::new);
    }

    public SalesTypeDto update(Long salesTypeId, SalesTypeUpdateDto salesTypeUpdateDto) {

        SalesType salesType = salesTypeRepository.findById(salesTypeId)
                .orElseThrow(NotFoundException::new);

        salesTypeUpdateDto.apply(salesType);

        return SalesTypeDto.of(salesType);
    }

    public void delete(Long salesTypeId) {

        SalesType salesType = salesTypeRepository.findById(salesTypeId)
                .orElseThrow(NotFoundException::new);

        salesTypeRepository.delete(salesType);
    }

    public Boolean exitstsByName(String name) {
        return salesTypeRepository.findByName(name);
    }
}
