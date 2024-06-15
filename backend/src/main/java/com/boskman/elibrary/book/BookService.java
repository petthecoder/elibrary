package com.boskman.elibrary.book;

import com.boskman.elibrary.author.Author;
import com.boskman.elibrary.author.AuthorRepository;
import com.boskman.elibrary.series.Series;
import com.boskman.elibrary.series.SeriesRepository;
import com.boskman.elibrary.series.SeriesRequest;
import com.boskman.elibrary.utils.BookScrapingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final SeriesRepository seriesRepository;
    private final BookScrapingService bookScrapingService;

    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Page<Book> searchBooksByTitle(String title, Pageable pageable) {
        return bookRepository.findByTitleContainingIgnoreCase(title, pageable);
    }

    @Transactional
    public Book createBook(BookRequest bookRequest) {
        Book book = Book.builder()
                .title(bookRequest.getTitle())
                .publicationYear(bookRequest.getPublicationYear())
                .genre(bookRequest.getGenre())
                .isbn(bookRequest.getIsbn())
                .format(bookRequest.getFormat())
                .synopsis(bookRequest.getSynopsis())
                .ordinal(bookRequest.getOrdinal())
                .build();

        populateSeriesAndAuthors(bookRequest, book);

        return bookRepository.save(book);
    }

    @Transactional
    public Book createBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElse(bookRepository
                        .save(bookScrapingService.createBookByIsbn(isbn)));
    }

    @Transactional
    public Book updateBook(Long bookId, BookRequest bookRequest) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookId));

        book.setTitle(bookRequest.getTitle());
        book.setPublicationYear(bookRequest.getPublicationYear());
        book.setGenre(bookRequest.getGenre());
        book.setIsbn(bookRequest.getIsbn());
        book.setFormat(bookRequest.getFormat());
        book.setSynopsis(bookRequest.getSynopsis());
        book.setSeries(null);
        book.setOrdinal(bookRequest.getOrdinal());

        populateSeriesAndAuthors(bookRequest, book);

        return bookRepository.save(book);
    }

    private void populateSeriesAndAuthors(BookRequest bookRequest, Book book) {
        if(bookRequest.getSeriesId() != null){
            Series series = seriesRepository.findById(bookRequest.getSeriesId())
                    .orElseThrow(() -> new EntityNotFoundException("Series not found with id: " + bookRequest.getSeriesId()));
            book.setSeries(series);
        }

        Set<Author> authors = new HashSet<>();
        for (Long authorId : bookRequest.getAuthors()) {
            Author author = authorRepository.findById(authorId)
                    .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + authorId));
            authors.add(author);
        }
        book.setAuthors(authors);
    }
}
