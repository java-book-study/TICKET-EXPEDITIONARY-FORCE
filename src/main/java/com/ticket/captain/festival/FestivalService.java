package com.ticket.captain.festival;

import com.ticket.captain.exception.NotFoundException;
import com.ticket.captain.festival.dto.FestivalCreateDto;
import com.ticket.captain.festival.dto.FestivalDto;
import com.ticket.captain.festival.dto.FestivalUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FestivalService {

    private final FestivalRepository festivalRepository;

    public FestivalDto add(FestivalCreateDto festivalCreateDto) {
        Festival festival = festivalCreateDto.toEntity();
        return FestivalDto.of(festivalRepository.save(festival));
    }

    @Transactional(readOnly = true)
    public List<FestivalDto> findAll(Pageable pageable) {
        return festivalRepository.findAll(pageable).stream()
                .map(FestivalDto::of)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FestivalDto findById(Long festivalId) {
        Optional<Festival> findFestival = festivalRepository.findById(festivalId);
        return findFestival
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
}
