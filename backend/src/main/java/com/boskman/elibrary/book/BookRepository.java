package com.boskman.elibrary.book;

import com.boskman.elibrary.series.Series;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookRepository extends JpaRepository<Book, Long> {


    Page<Book> findAll(Pageable pageable);

    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Set<Book> findBySeries(Series series);

    List<Book> findByAuthors_AuthorId(Long authorId);

    Optional<Book> findByIsbn(String isbn);
}
