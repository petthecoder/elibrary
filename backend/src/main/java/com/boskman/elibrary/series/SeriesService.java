package com.boskman.elibrary.series;

import com.boskman.elibrary.book.Book;
import com.boskman.elibrary.book.BookDto;
import com.boskman.elibrary.book.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeriesService {

    private final SeriesRepository seriesRepository;

    private final BookRepository bookRepository;

    @Transactional
    public Series createSeries(SeriesRequest seriesRequest) {
        Series series = Series.builder()
                .title(seriesRequest.getTitle())
                .description(seriesRequest.getDescription())
                .build();
        return seriesRepository.save(series);
    }

    @Transactional
    public Series updateSeries(Long seriesId, SeriesRequest seriesRequest) {
        Series series = seriesRepository.findById(seriesId)
                .orElseThrow(() -> new EntityNotFoundException("Series not found with id: " + seriesId));

        series.setTitle(seriesRequest.getTitle());
        series.setDescription(seriesRequest.getDescription());

        return seriesRepository.save(series);
    }

    public Page<Series> getAllSeries(Pageable pageable) {
        return seriesRepository.findAll(pageable);
    }

    public Page<Series> searchSeriesByTitle(String title, Pageable pageable) {
        return seriesRepository.findByTitleContainingIgnoreCase(title, pageable);
    }

    public SeriesDto getSeriesWithBooks(Long seriesId) {
        Series series = seriesRepository.findById(seriesId)
                .orElseThrow(() -> new EntityNotFoundException("Series not found with id: " + seriesId));

        Set<Book> books = bookRepository.findBySeries(series);
        List<BookDto> bookDTOs = books.stream().map(book -> {
            return BookDto.builder()
                    .bookId(book.getBookId())
                    .title(book.getTitle())
                    .publicationYear(book.getPublicationYear())
                    .genre(book.getGenre())
                    .isbn(book.getIsbn())
                    .format(book.getFormat())
                    .ordinal(book.getOrdinal())
                    .build();
        }).collect(Collectors.toList());

        return SeriesDto.builder()
                .seriesId(series.getSeriesId())
                .title(series.getTitle())
                .description(series.getDescription())
                .books(bookDTOs)
                .build();
    }
}
