package com.boskman.elibrary.listbook;

import com.boskman.elibrary.book.Book;
import com.boskman.elibrary.list.List;
import com.boskman.elibrary.listbook.ListBookId;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "list_books")
public class ListBook {
    @EmbeddedId
    private ListBookId id;

    @ManyToOne
    @MapsId("listId")
    @JoinColumn(name = "list_id")
    private List list;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    private Book book;
}

