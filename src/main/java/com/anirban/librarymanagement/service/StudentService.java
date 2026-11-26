package com.anirban.librarymanagement.service;

import com.anirban.librarymanagement.dto.StudentRequest;
import com.anirban.librarymanagement.dto.StudentResponse;
import com.anirban.librarymanagement.entity.Student;
import com.anirban.librarymanagement.exception.BusinessRuleException;
import com.anirban.librarymanagement.exception.DuplicateResourceException;
import com.anirban.librarymanagement.exception.ResourceNotFoundException;
import com.anirban.librarymanagement.repository.BookIssueRepository;
import com.anirban.librarymanagement.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository students;
    private final BookIssueRepository issues;
    public StudentService(StudentRepository students, BookIssueRepository issues) {
        this.students = students; this.issues = issues;
    }
    @Transactional
    public StudentResponse register(StudentRequest request) {
        if (students.existsByEmail(request.email())) throw new DuplicateResourceException("Email already registered");
        Student student = new Student(); apply(student, request);
        return StudentResponse.from(students.save(student));
    }
    @Transactional(readOnly = true)
    public List<StudentResponse> all() { return students.findAll().stream().map(StudentResponse::from).toList(); }
    @Transactional(readOnly = true)
    public StudentResponse byId(Long id) { return StudentResponse.from(find(id)); }
    @Transactional
    public StudentResponse update(Long id, StudentRequest request) {
        Student student = find(id);
        if (students.existsByEmailAndStudentIdNot(request.email(), id)) throw new DuplicateResourceException("Email already registered");
        apply(student, request);
        return StudentResponse.from(students.save(student));
    }
    @Transactional
    public void delete(Long id) {
        Student student = find(id);
        if (issues.existsByStudent_StudentId(id)) throw new BusinessRuleException("Cannot delete a student with issue history");
        students.delete(student);
    }
    private Student find(Long id) {
        return students.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found: " + id));
    }
    private void apply(Student student, StudentRequest request) {
        student.setStudentName(request.studentName().trim()); student.setEmail(request.email().trim());
        student.setDepartment(request.department().trim()); student.setPhoneNumber(request.phoneNumber().trim());
    }
}
