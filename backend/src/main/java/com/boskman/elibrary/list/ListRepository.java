package com.boskman.elibrary.list;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ListRepository extends JpaRepository<List, Long> {
    Set<List> findByUser_UserId(Long userId);
}
