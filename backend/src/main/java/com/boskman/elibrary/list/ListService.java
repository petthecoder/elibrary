package com.boskman.elibrary.list;

import com.boskman.elibrary.book.Book;
import com.boskman.elibrary.book.BookRepository;
import com.boskman.elibrary.listbook.ListBookId;
import com.boskman.elibrary.listbook.ListBookRepository;
import com.boskman.elibrary.user.User;
import com.boskman.elibrary.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ListService {

    private final ListRepository listRepository;
    private final ListBookRepository listBookRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Transactional
    public List createList(ListRequest listRequest) {

        User user = getUser();

        List list = List.builder()
                .title(listRequest.getTitle())
                .description(listRequest.getDescription())
                .user(user)
                .isPublic(false)
                .build();

        return listRepository.save(list);
    }

    public List updateList(Long listId, ListRequest listRequest) throws AccessDeniedException {

        List list = listRepository.findById(listId)
                .orElseThrow(() -> new EntityNotFoundException("List not found with id: " + listId));

        checkAccess(list.getUser());

        list.setTitle(listRequest.getTitle());
        list.setTitle(listRequest.getDescription());

        return listRepository.save(list);
    }

    public Set<List> getAllLists() {

        User user = getUser();

        return listRepository.findByUser_UserId(user.getUserId());
    }

    @Transactional
    public List addBookToList(Long listId, Long bookId) throws AccessDeniedException {
        List list = listRepository.findById(listId)
                .orElseThrow(() -> new EntityNotFoundException("List not found with id: " + listId));

        checkAccess(list.getUser());

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookId));

        list.getBooks().add(book);
        return listRepository.save(list);
    }

    @Transactional
    public void removeBookFromList(Long listId, Long bookId) throws AccessDeniedException {

        List list = listRepository.findById(listId)
                .orElseThrow(() -> new EntityNotFoundException("List not found with id: " + listId));

        checkAccess(list.getUser());

        ListBookId listBookId = new ListBookId();
        listBookId.setListId(listId);
        listBookId.setBookId(bookId);

        listBookRepository.deleteById(listBookId);
    }

    @Transactional
    public void deleteList(Long listId) throws AccessDeniedException {

        List list = listRepository.findById(listId)
                .orElseThrow(() -> new EntityNotFoundException("List not found with id: " + listId));

        checkAccess(list.getUser());

        listRepository.deleteById(listId);
    }

    private void checkAccess(User listUser) throws AccessDeniedException {
        User user = getUser();
        if (!user.equals(listUser)) {
            throw new AccessDeniedException("You don't have access to this list");
        }
    }

    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));
    }
}
