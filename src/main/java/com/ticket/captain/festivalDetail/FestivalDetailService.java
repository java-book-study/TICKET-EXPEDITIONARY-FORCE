package com.ticket.captain.festivalDetail;

import com.ticket.captain.exception.NotFoundException;
import com.ticket.captain.festival.Festival;
import com.ticket.captain.festival.FestivalRepository;
import com.ticket.captain.festivalDetail.dto.FestivalDetailCreateDto;
import com.ticket.captain.festivalDetail.dto.FestivalDetailDto;
import com.ticket.captain.festivalDetail.dto.FestivalDetailUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FestivalDetailService {

    private final FestivalDetailRepository festivalDetailRepository;
    private final FestivalRepository festivalRepository;

    public FestivalDetailDto add(Long festivalId, FestivalDetailCreateDto festivalDetailCreateDto) {
        FestivalDetail festivalDetail = festivalDetailCreateDto.toEntity();
        Festival findFestival = festivalRepository.findById(festivalId).orElseThrow(NotFoundException::new);
        festivalDetail.setFestival(findFestival);
        return FestivalDetailDto.of(festivalDetailRepository.save(festivalDetailCreateDto.toEntity()));
    }

    @Transactional(readOnly = true)
    public List<FestivalDetailDto> findAll(Pageable pageable) {
        return festivalDetailRepository.findAll(pageable).stream()
                .map(FestivalDetailDto::of)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FestivalDetailDto findById(Long festivalDetailId) {
        return festivalDetailRepository.findById(festivalDetailId)
                .map(FestivalDetailDto::of)
                .orElseThrow(NotFoundException::new);
    }

    public void delete(Long festivalDetailId) {
        FestivalDetail festivalDetail = festivalDetailRepository.findById(festivalDetailId)
                .orElseThrow(NotFoundException::new);

        festivalDetailRepository.delete(festivalDetail);
    }

    public FestivalDetailDto update(Long festivalDetailId, FestivalDetailUpdateDto festivalDetailUpdateDto) {
        FestivalDetail festivalDetail = festivalDetailRepository.findById(festivalDetailId)
                .orElseThrow(NotFoundException::new);

        festivalDetailUpdateDto.apply(festivalDetail);
        return FestivalDetailDto.of(festivalDetail);
    }
}
