package com.boskman.elibrary.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private Long bookId;
    private String title;
    private Integer publicationYear;
    private String genre;
    private String isbn;
    private String format;
    private String synopsis;
    private Integer ordinal;
}
