package com.ticket.captain.festival;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public interface FestivalRepository extends JpaRepository<Festival, Long> {

    @Override
    Page<Festival> findAll(Pageable pageable);

    @Override
    void deleteById(Long festivalId);

    Festival findByTitle(String title);
}
