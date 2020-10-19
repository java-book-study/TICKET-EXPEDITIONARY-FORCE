package com.ticket.captain.festival;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface FestivalRepository extends JpaRepository<Festival, Long> {

    Optional<Festival> findById(Long id);

    @Override
    Page<Festival> findAll(Pageable pageable);
}
