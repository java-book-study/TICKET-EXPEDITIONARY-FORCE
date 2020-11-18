package com.ticket.captain.festivalCategory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FestivalCategoryRepository extends JpaRepository<FestivalCategory, Long> {
    FestivalCategory findByCategoryName(String categoryName);
}
