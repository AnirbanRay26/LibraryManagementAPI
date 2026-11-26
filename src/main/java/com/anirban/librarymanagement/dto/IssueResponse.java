package com.anirban.librarymanagement.dto;

import com.anirban.librarymanagement.entity.BookIssue;
import com.anirban.librarymanagement.entity.IssueStatus;
import java.time.LocalDate;

public record IssueResponse(Long issueId, Long studentId, String studentName,
                            Long bookId, String bookTitle, LocalDate issueDate,
                            LocalDate dueDate, LocalDate returnDate, IssueStatus status) {
    public static IssueResponse from(BookIssue issue) {
        return new IssueResponse(issue.getIssueId(), issue.getStudent().getStudentId(),
                issue.getStudent().getStudentName(), issue.getBook().getBookId(),
                issue.getBook().getTitle(), issue.getIssueDate(), issue.getDueDate(),
                issue.getReturnDate(), issue.getStatus());
    }
}
