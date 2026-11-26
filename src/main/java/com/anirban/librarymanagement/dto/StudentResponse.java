package com.anirban.librarymanagement.dto;

import com.anirban.librarymanagement.entity.Student;

public record StudentResponse(Long studentId, String studentName, String email,
                              String department, String phoneNumber) {
    public static StudentResponse from(Student student) {
        return new StudentResponse(student.getStudentId(), student.getStudentName(), student.getEmail(),
                student.getDepartment(), student.getPhoneNumber());
    }
}
