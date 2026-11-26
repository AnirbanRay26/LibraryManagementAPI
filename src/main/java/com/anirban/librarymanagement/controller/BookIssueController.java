package com.anirban.librarymanagement.controller;

import com.anirban.librarymanagement.dto.IssueResponse;
import com.anirban.librarymanagement.service.BookIssueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class BookIssueController {
    private final BookIssueService service;
    public BookIssueController(BookIssueService service) { this.service = service; }
    @PostMapping("/{studentId}/{bookId}")
    public ResponseEntity<IssueResponse> issue(@PathVariable Long studentId, @PathVariable Long bookId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.issue(studentId, bookId));
    }
    @GetMapping public List<IssueResponse> all() { return service.all(); }
    @GetMapping("/student/{studentId}") public List<IssueResponse> byStudent(@PathVariable Long studentId) {
        return service.byStudent(studentId);
    }
    @PutMapping("/return/{issueId}") public IssueResponse returnBook(@PathVariable Long issueId) {
        return service.returnBook(issueId);
    }
    @GetMapping("/overdue") public List<IssueResponse> overdue() { return service.overdue(); }
}
