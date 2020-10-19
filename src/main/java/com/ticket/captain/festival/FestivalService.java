package com.ticket.captain.festival;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    public Festival updateFestival(Long festivalId, FestivalRequest festivalRequest) {
        Festival festival = festivalRepository.findById(festivalId)
                .orElseThrow(NullPointerException::new);
        festival.update(festivalRequest);
        return festivalRepository.save(festival);
    }

    @Transactional(readOnly = true)
    public Optional<Festival> findById(Long festival_id) {
        return festivalRepository.findById(festival_id);
    }

    public void deleteFestival(Long festivalId) {
        festivalRepository.deleteById(festivalId);
    }


    @Transactional(readOnly = true)
    public List<Festival> getFestivals(Pageable pageable) {
        return festivalRepository.findAll(pageable).getContent();
    }
}
