package com.boskman.elibrary.author;

import com.boskman.elibrary.book.Book;
import com.boskman.elibrary.book.BookRequest;
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
@RequestMapping("/api/v1/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    private final PagedResourcesAssembler<Author> pagedResourcesAssembler;

    @GetMapping(value = "")
    public ResponseEntity<PagedModel<EntityModel<Author>>> getAllAuthors(Pageable pageable) {
        Page<Author> authors = authorService.getAllAuthors(pageable);
        PagedModel<EntityModel<Author>> pagedModel = pagedResourcesAssembler.toModel(authors, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AuthorController.class).getAllAuthors(pageable)).withSelfRel());
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/search")
    public ResponseEntity<PagedModel<?>> searchAuthors(@RequestParam String name, Pageable pageable) {
        Page<Author> authors = authorService.searchAuthorsByName(name, pageable);
        PagedModel<?> pagedModel = pagedResourcesAssembler.toModel(authors, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AuthorController.class).searchAuthors(name, pageable)).withSelfRel());
        return ResponseEntity.ok(pagedModel);
    }

    @PostMapping(value = "")
    public ResponseEntity<Author> createAuthor(@RequestBody AuthorRequest authorRequest) {
        Author createdAuthor = authorService.createAuthor(authorRequest);
        return ResponseEntity.ok(createdAuthor);
    }

    @PutMapping("/{authorId}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long authorId, @RequestBody AuthorRequest authorRequest) {
        Author updatedAuthor = authorService.updateAuthor(authorId, authorRequest);
        return ResponseEntity.ok(updatedAuthor);
    }

    @GetMapping("/{authorId}/books")
    public ResponseEntity<AuthorBooksDto> getAuthorWithBooks(@PathVariable Long authorId) {
        AuthorBooksDto dto = authorService.getAuthorWithBooks(authorId);
        return ResponseEntity.ok(dto);
    }
}

