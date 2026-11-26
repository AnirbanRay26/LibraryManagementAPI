package com.anirban.librarymanagement.service;

import com.anirban.librarymanagement.dto.IssueResponse;
import com.anirban.librarymanagement.entity.Book;
import com.anirban.librarymanagement.entity.BookIssue;
import com.anirban.librarymanagement.entity.IssueStatus;
import com.anirban.librarymanagement.entity.Student;
import com.anirban.librarymanagement.exception.BusinessRuleException;
import com.anirban.librarymanagement.exception.NoCopiesAvailableException;
import com.anirban.librarymanagement.exception.ResourceNotFoundException;
import com.anirban.librarymanagement.repository.BookIssueRepository;
import com.anirban.librarymanagement.repository.BookRepository;
import com.anirban.librarymanagement.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class BookIssueService {
    private static final int LOAN_PERIOD_DAYS = 14;
    private static final int MAX_ACTIVE_ISSUES = 3;
    private final BookIssueRepository issues;
    private final BookRepository books;
    private final StudentRepository students;
    public BookIssueService(BookIssueRepository issues, BookRepository books, StudentRepository students) {
        this.issues = issues; this.books = books; this.students = students;
    }
    @Transactional
    public IssueResponse issue(Long studentId, Long bookId) {
        Student student = students.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + studentId));
        Book book = books.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + bookId));
        if (book.getAvailableCopies() < 1) throw new NoCopiesAvailableException("No copies available for this book");
        if (issues.countByStudent_StudentIdAndStatus(studentId, IssueStatus.ISSUED) >= MAX_ACTIVE_ISSUES)
            throw new BusinessRuleException("A student can have at most three active issues");

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        books.save(book);
        LocalDate today = LocalDate.now();
        BookIssue issue = new BookIssue();
        issue.setStudent(student); issue.setBook(book); issue.setIssueDate(today);
        issue.setDueDate(today.plusDays(LOAN_PERIOD_DAYS)); issue.setStatus(IssueStatus.ISSUED);
        return IssueResponse.from(issues.save(issue));
    }
    @Transactional(readOnly = true)
    public List<IssueResponse> all() { return issues.findAll().stream().map(IssueResponse::from).toList(); }
    @Transactional(readOnly = true)
    public List<IssueResponse> byStudent(Long studentId) {
        if (!students.existsById(studentId)) throw new ResourceNotFoundException("Student not found: " + studentId);
        return issues.findByStudent_StudentId(studentId).stream().map(IssueResponse::from).toList();
    }
    @Transactional
    public IssueResponse returnBook(Long issueId) {
        BookIssue issue = issues.findById(issueId)
                .orElseThrow(() -> new ResourceNotFoundException("Issue record not found: " + issueId));
        if (issue.getStatus() == IssueStatus.RETURNED) throw new BusinessRuleException("This book has already been returned");
        issue.setReturnDate(LocalDate.now()); issue.setStatus(IssueStatus.RETURNED);
        Book book = issue.getBook(); book.setAvailableCopies(book.getAvailableCopies() + 1);
        books.save(book);
        return IssueResponse.from(issues.save(issue));
    }
    @Transactional(readOnly = true)
    public List<IssueResponse> overdue() {
        return issues.findByStatusAndDueDateBefore(IssueStatus.ISSUED, LocalDate.now())
                .stream().map(IssueResponse::from).toList();
    }
}
