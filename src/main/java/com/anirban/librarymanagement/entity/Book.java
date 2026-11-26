package com.anirban.librarymanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    @Column(nullable = false, length = 150)
    private String title;
    @Column(nullable = false, length = 120)
    private String author;
    @Column(nullable = false, unique = true, length = 30)
    private String isbn;
    @Column(nullable = false, length = 80)
    private String category;
    @Column(nullable = false)
    private Integer totalCopies;
    @Column(nullable = false)
    private Integer availableCopies;

    public Book() {}
    public Long getBookId() { return bookId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public Integer getTotalCopies() { return totalCopies; }
    public void setTotalCopies(Integer totalCopies) { this.totalCopies = totalCopies; }
    public Integer getAvailableCopies() { return availableCopies; }
    public void setAvailableCopies(Integer availableCopies) { this.availableCopies = availableCopies; }
}
