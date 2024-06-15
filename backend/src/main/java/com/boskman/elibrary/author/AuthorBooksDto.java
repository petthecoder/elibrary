package com.boskman.elibrary.author;

import com.boskman.elibrary.book.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorBooksDto {
    private Long authorId;
    private String name;
    private String photoUrl;
    private List<Book> books;
}
