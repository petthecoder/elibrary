package com.boskman.elibrary.series;

import com.boskman.elibrary.book.BookDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeriesDto {
    private Long seriesId;
    private String title;
    private String description;
    private List<BookDto> books;
}
