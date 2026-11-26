package com.anirban.librarymanagement.repository;

import com.anirban.librarymanagement.entity.BookIssue;
import com.anirban.librarymanagement.entity.IssueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface BookIssueRepository extends JpaRepository<BookIssue, Long> {
    long countByStudent_StudentIdAndStatus(Long studentId, IssueStatus status);
    List<BookIssue> findByStudent_StudentId(Long studentId);
    List<BookIssue> findByStatusAndDueDateBefore(IssueStatus status, LocalDate date);
    boolean existsByBook_BookId(Long bookId);
    boolean existsByStudent_StudentId(Long studentId);
}
