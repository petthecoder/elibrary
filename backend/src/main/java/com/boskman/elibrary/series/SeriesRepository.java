package com.boskman.elibrary.series;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeriesRepository extends JpaRepository<Series, Long> {
    Page<Series> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
