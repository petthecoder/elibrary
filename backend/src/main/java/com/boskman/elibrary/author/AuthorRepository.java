package com.boskman.elibrary.author;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Page<Author> findAll(Pageable pageable);

    Page<Author> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
