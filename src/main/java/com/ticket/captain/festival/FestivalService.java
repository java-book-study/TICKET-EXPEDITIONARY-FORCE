package com.ticket.captain.festival;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FestivalService {

    private final FestivalRepository festivalRepository;

    public FestivalService(FestivalRepository festivalRepository) {
        this.festivalRepository = festivalRepository;
    }

    @Transactional
    public Festival generate(Festival newFestival) {
        return festivalRepository.save(newFestival);
    }


    @Transactional(readOnly = true)
    public Optional<Festival> findById(Long fastival_id) {
        return festivalRepository.findById(fastival_id);
    }

    @Transactional(readOnly = true)
    public List<Festival> getFestivals(Pageable pageable) {
        return festivalRepository.findAll(pageable).getContent();
    }
}
