package com.ticket.captain.festival;

import com.ticket.captain.exception.NotFoundException;
import com.ticket.captain.festival.dto.FestivalCreateDto;
import com.ticket.captain.festival.dto.FestivalDto;
import com.ticket.captain.festival.dto.FestivalUpdateDto;
import com.ticket.captain.festivalCategory.FestivalCategory;
import com.ticket.captain.festivalCategory.FestivalCategoryService;
import com.ticket.captain.festivalCategory.dto.FestivalCategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FestivalService {

    private final FestivalRepository festivalRepository;

    private final FestivalCategoryService festivalCategoryService;

    public FestivalDto add(FestivalCreateDto festivalCreateDto) {
        Festival festival = festivalCreateDto.toEntity();
        FestivalCategoryDto festivalCategoryDto = festivalCategoryService.findById(festivalCreateDto.getCategoryId());
        FestivalCategory festivalCategory = festivalCategoryService.findByCategoryName(festivalCategoryDto.getCategoryName());
        festival.addCategory(festivalCategory);
        return FestivalDto.of(festivalRepository.save(festival));
    }

    @Transactional(readOnly = true)
    public List<FestivalDto> findAll(int offset, int limit) {

        PageRequest pageRequest = PageRequest.of(offset, limit);

        return festivalRepository.findAll(pageRequest).stream()
                .map(FestivalDto::of)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FestivalDto findById(Long festivalId) {
        return festivalRepository.findById(festivalId)
                .map(FestivalDto::of)
                .orElseThrow(NotFoundException::new);
    }

    public void delete(Long festivalId) {
        Festival festival = festivalRepository.findById(festivalId)
                .orElseThrow(NotFoundException::new);

        festivalRepository.delete(festival);
    }

    public FestivalDto update(Long festivalId, FestivalUpdateDto festivalUpdateDto) {
        Festival festival = festivalRepository.findById(festivalId)
                .orElseThrow(NotFoundException::new);
        festivalUpdateDto.apply(festival);
        return FestivalDto.of(festival);
    }

    @Transactional(readOnly = true)
    public Festival findByTitle(String title) {
        return festivalRepository.findByTitle(title);
    }

    public FestivalDto addCategory(FestivalCategory category, Long festivalId) {
        Festival festival = festivalRepository.findById(festivalId)
                .orElseThrow(NotFoundException::new);
        festival.addCategory(category);
        festivalRepository.save(festival);
        return FestivalDto.of(festival);
    }
}
