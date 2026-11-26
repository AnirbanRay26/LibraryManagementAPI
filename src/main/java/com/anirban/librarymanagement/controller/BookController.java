package com.anirban.librarymanagement.controller;

import com.anirban.librarymanagement.dto.BookRequest;
import com.anirban.librarymanagement.dto.BookResponse;
import com.anirban.librarymanagement.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService service;
    public BookController(BookService service) { this.service = service; }
    @PostMapping public ResponseEntity<BookResponse> add(@Valid @RequestBody BookRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.add(request));
    }
    @GetMapping public List<BookResponse> all() { return service.all(); }
    @GetMapping("/{id}") public BookResponse byId(@PathVariable Long id) { return service.byId(id); }
    @PutMapping("/{id}") public BookResponse update(@PathVariable Long id, @Valid @RequestBody BookRequest request) {
        return service.update(id, request);
    }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id); return ResponseEntity.noContent().build();
    }
    @GetMapping("/category/{category}") public List<BookResponse> byCategory(@PathVariable String category) {
        return service.byCategory(category);
    }
}
