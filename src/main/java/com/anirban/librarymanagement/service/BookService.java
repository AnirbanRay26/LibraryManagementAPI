package com.anirban.librarymanagement.service;

import com.anirban.librarymanagement.dto.BookRequest;
import com.anirban.librarymanagement.dto.BookResponse;
import com.anirban.librarymanagement.entity.Book;
import com.anirban.librarymanagement.exception.BusinessRuleException;
import com.anirban.librarymanagement.exception.DuplicateResourceException;
import com.anirban.librarymanagement.exception.ResourceNotFoundException;
import com.anirban.librarymanagement.repository.BookIssueRepository;
import com.anirban.librarymanagement.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class BookService {
    private final BookRepository books;
    private final BookIssueRepository issues;
    public BookService(BookRepository books, BookIssueRepository issues) {
        this.books = books; this.issues = issues;
    }

    @Transactional
    public BookResponse add(BookRequest request) {
        if (books.existsByIsbn(request.isbn())) throw new DuplicateResourceException("ISBN already exists");
        Book book = new Book();
        apply(book, request);
        book.setAvailableCopies(request.totalCopies());
        return BookResponse.from(books.save(book));
    }
    @Transactional(readOnly = true)
    public List<BookResponse> all() { return books.findAll().stream().map(BookResponse::from).toList(); }
    @Transactional(readOnly = true)
    public BookResponse byId(Long id) { return BookResponse.from(find(id)); }
    @Transactional
    public BookResponse update(Long id, BookRequest request) {
        Book book = find(id);
        if (books.existsByIsbnAndBookIdNot(request.isbn(), id)) throw new DuplicateResourceException("ISBN already exists");
        int borrowed = book.getTotalCopies() - book.getAvailableCopies();
        if (request.totalCopies() < borrowed) throw new BusinessRuleException("Total copies cannot be less than copies currently issued");
        apply(book, request);
        book.setAvailableCopies(request.totalCopies() - borrowed);
        return BookResponse.from(books.save(book));
    }
    @Transactional
    public void delete(Long id) {
        Book book = find(id);
        if (issues.existsByBook_BookId(id)) throw new BusinessRuleException("Cannot delete a book that has issue history");
        books.delete(book);
    }
    @Transactional(readOnly = true)
    public List<BookResponse> byCategory(String category) {
        return books.findByCategoryIgnoreCase(category).stream().map(BookResponse::from).toList();
    }
    private Book find(Long id) {
        return books.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found: " + id));
    }
    private void apply(Book book, BookRequest request) {
        book.setTitle(request.title().trim()); book.setAuthor(request.author().trim());
        book.setIsbn(request.isbn().trim()); book.setCategory(request.category().trim());
        book.setTotalCopies(request.totalCopies());
    }
}
