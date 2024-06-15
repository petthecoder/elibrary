package com.boskman.elibrary.book;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final PagedResourcesAssembler<Book> pagedResourcesAssembler;

    @GetMapping(value = "")
    public ResponseEntity<PagedModel<EntityModel<Book>>> getAllBooks(Pageable pageable) {
        Page<Book> books = bookService.getAllBooks(pageable);
        PagedModel<EntityModel<Book>> pagedModel = pagedResourcesAssembler.toModel(books, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getAllBooks(pageable)).withSelfRel());
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/search")
    public ResponseEntity<PagedModel<?>> searchBooks(@RequestParam String title, Pageable pageable) {
        Page<Book> books = bookService.searchBooksByTitle(title, pageable);
        PagedModel<?> pagedModel = pagedResourcesAssembler.toModel(books, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).searchBooks(title, pageable)).withSelfRel());
        return ResponseEntity.ok(pagedModel);
    }

    @PostMapping(value = "")
    public ResponseEntity<Book> createBook(@RequestBody BookRequest bookRequest) {
        Book createdBook = bookService.createBook(bookRequest);
        return ResponseEntity.ok(createdBook);
    }

    @PostMapping(value = "/isbn/{isbn}")
    public ResponseEntity<Book> createBookByIsbn(@PathVariable String isbn) {
        Book createdBook = bookService.createBookByIsbn(isbn);
        return ResponseEntity.ok(createdBook);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable Long bookId, @RequestBody BookRequest bookRequest) {
        Book updatedBooks = bookService.updateBook(bookId, bookRequest);
        return ResponseEntity.ok(updatedBooks);
    }
}
