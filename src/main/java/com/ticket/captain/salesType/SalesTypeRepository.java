package com.ticket.captain.salesType;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesTypeRepository extends JpaRepository<SalesType, Long> {
    Boolean findByName(String name);
}
