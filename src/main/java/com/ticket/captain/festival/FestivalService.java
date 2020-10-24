package com.ticket.captain.festival;

import com.ticket.captain.festival.dto.FestivalCreateDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
@Transactional
public class FestivalService {

    private final FestivalRepository festivalRepository;

    public FestivalService(FestivalRepository festivalRepository) {
        this.festivalRepository = festivalRepository;
    }

    public Festival generate(Festival newFestival) {
        return festivalRepository.save(newFestival);
    }

    public Festival updateFestival(Long festivalId, FestivalCreateDto festivalCreateDto) {
        checkNotNull(festivalId, "festivalId must be provided.");
        Festival festival = festivalRepository.findById(festivalId)
                .orElseThrow(NullPointerException::new);
        festival.update(festivalCreateDto);
        return festivalRepository.save(festival);
    }

    @Transactional(readOnly = true)
    public Optional<Festival> findById(Long festivalId) {
        checkNotNull(festivalId, "festivalId must be provided.");
        return festivalRepository.findById(festivalId);
    }

    @Transactional(readOnly = true)
    public Boolean existsByName(String name) {
        checkNotNull(name, "name must be provided.");
        return festivalRepository.existsByName(name);}

    public void deleteFestival(Long festivalId) {
        checkNotNull(festivalId, "festivalId must be provided.");
        festivalRepository.deleteById(festivalId);
    }


    @Transactional(readOnly = true)
    public List<Festival> getFestivals(Pageable pageable) {
        return festivalRepository.findAll(pageable).getContent();
    }
}
