package com.boskman.elibrary.list;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/lists")
@RequiredArgsConstructor
public class ListController {

    private final ListService listService;

    @GetMapping
    public ResponseEntity<Set<List>> getAllLists() {
        Set<List> userLists = listService.getAllLists();
        return ResponseEntity.ok(userLists);
    }

    @PutMapping("/{listId}/addBook/{bookId}")
    public ResponseEntity<List> addBookToList(@PathVariable Long listId, @PathVariable Long bookId) throws AccessDeniedException {
        List updatedList = listService.addBookToList(listId, bookId);
        return ResponseEntity.ok(updatedList);
    }

    @PutMapping("/{listId}/removeBook/{bookId}")
    public ResponseEntity<Void> removeBookFromList(@PathVariable Long listId, @PathVariable Long bookId) throws AccessDeniedException {
        listService.removeBookFromList(listId, bookId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<List> createList(@RequestBody ListRequest listRequest) {
        List createdList = listService.createList(listRequest);
        return ResponseEntity.ok(createdList);
    }

    @DeleteMapping("/{listId}")
    public ResponseEntity<Void> deleteList(@PathVariable Long listId) throws AccessDeniedException {
        listService.deleteList(listId);
        return ResponseEntity.noContent().build();
    }
}

