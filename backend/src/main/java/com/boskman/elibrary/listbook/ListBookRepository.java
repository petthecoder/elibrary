package com.boskman.elibrary.listbook;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListBookRepository extends JpaRepository<ListBook, ListBookId> {
    void deleteById(ListBookId id);
}
