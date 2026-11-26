package com.anirban.librarymanagement.repository;

import com.anirban.librarymanagement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndStudentIdNot(String email, Long studentId);
}
