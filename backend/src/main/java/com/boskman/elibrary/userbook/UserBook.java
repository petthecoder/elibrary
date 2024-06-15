package com.boskman.elibrary.userbook;

import com.boskman.elibrary.book.Book;
import com.boskman.elibrary.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_books")
public class UserBook {
    @EmbeddedId
    private UserBookId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    private Book book;

    private String status; // 'want_to_read', 'currently_reading', 'read'

    private Integer rating;

    private LocalDate purchaseDate;

    private LocalDate startDate;

    private LocalDate finishDate;
}

