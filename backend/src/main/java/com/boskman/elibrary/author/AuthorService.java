package com.boskman.elibrary.author;

import com.boskman.elibrary.book.Book;
import com.boskman.elibrary.book.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public Page<Author> getAllAuthors(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    public Page<Author> searchAuthorsByName(String name, Pageable pageable) {
        return authorRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    public Author createAuthor(AuthorRequest authorRequest) {
        Author author = Author.builder()
                .name(authorRequest.getName())
                .photoUrl(authorRequest.getPhotoUrl())
                .build();
        return authorRepository.save(author);
    }

    @Transactional
    public Author updateAuthor(Long authorId, AuthorRequest authorRequest) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Series not found with id: " + authorId));

        author.setName(authorRequest.getName());
        author.setPhotoUrl(authorRequest.getPhotoUrl());

        return authorRepository.save(author);
    }

    public AuthorBooksDto getAuthorWithBooks(Long authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new EntityNotFoundException("Author not found"));
        List<Book> books = bookRepository.findByAuthors_AuthorId(authorId);

        return AuthorBooksDto.builder()
                .authorId(author.getAuthorId())
                .name(author.getName())
                .photoUrl(author.getPhotoUrl())
                .books(books)
                .build();
    }


}
