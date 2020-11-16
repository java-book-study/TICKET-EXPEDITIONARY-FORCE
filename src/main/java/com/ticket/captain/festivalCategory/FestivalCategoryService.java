package com.ticket.captain.festivalCategory;

import com.ticket.captain.exception.NotFoundException;
import com.ticket.captain.festivalCategory.dto.FestivalCategoryCreateDto;
import com.ticket.captain.festivalCategory.dto.FestivalCategoryDto;
import com.ticket.captain.festivalCategory.dto.FestivalCategoryUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FestivalCategoryService {

    private final FestivalCategoryRepository festivalCategoryRepository;

    public FestivalCategoryDto add(FestivalCategoryCreateDto festivalCategoryCreateDto) {
        FestivalCategory newFestivalCategory = festivalCategoryCreateDto.toEntity();

        return FestivalCategoryDto.of(festivalCategoryRepository.save(newFestivalCategory));
    }

    @Transactional(readOnly = true)
    public List<FestivalCategoryDto> findAll(int offset, int limit) {

    PageRequest pageRequest = PageRequest.of(offset, limit);

        return festivalCategoryRepository.findAll(pageRequest).stream()
                .map(FestivalCategoryDto::of)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FestivalCategoryDto findById(Long festivalCategoryId) {

        return festivalCategoryRepository.findById(festivalCategoryId)
                .map(FestivalCategoryDto::of)
                .orElseThrow(NotFoundException::new);
    }

    public FestivalCategoryDto update(Long festivalCategoryId, FestivalCategoryUpdateDto festivalCategoryUpdateDto) {
        FestivalCategory festivalCategory = festivalCategoryRepository.findById(festivalCategoryId)
                .orElseThrow(NotFoundException::new);

        festivalCategoryUpdateDto.apply(festivalCategory);

        return FestivalCategoryDto.of(festivalCategory);
    }

    public void delete(Long festivalCategoryId) {

        FestivalCategory festivalCategory = festivalCategoryRepository.findById(festivalCategoryId)
                .orElseThrow(NotFoundException::new);

        festivalCategoryRepository.delete(festivalCategory);
    }

    public FestivalCategory findByCategoryName(String categoryName) {
        return festivalCategoryRepository.findByCategoryName(categoryName);
    }


}
