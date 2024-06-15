package com.boskman.elibrary.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {
    private String title;
    private Integer publicationYear;
    private String genre;
    private String isbn;
    private String format;
    private String synopsis;
    private Set<Long> authors;
    private Long seriesId;
    private Integer ordinal;
}
