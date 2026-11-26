package com.anirban.librarymanagement.repository;

import com.anirban.librarymanagement.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByIsbn(String isbn);

    boolean existsByIsbnAndBookIdNot(String isbn, Long bookId);

    List<Book> findByCategoryIgnoreCase(String category);

    // Search books by author name
    List<Book> findByAuthorContainingIgnoreCase(String author);
}