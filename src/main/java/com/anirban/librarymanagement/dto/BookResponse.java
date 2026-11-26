package com.anirban.librarymanagement.dto;

import com.anirban.librarymanagement.entity.Book;

public record BookResponse(Long bookId, String title, String author, String isbn,
                           String category, Integer totalCopies, Integer availableCopies) {
    public static BookResponse from(Book book) {
        return new BookResponse(book.getBookId(), book.getTitle(), book.getAuthor(), book.getIsbn(),
                book.getCategory(), book.getTotalCopies(), book.getAvailableCopies());
    }
}
