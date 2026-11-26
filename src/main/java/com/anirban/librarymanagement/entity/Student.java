package com.anirban.librarymanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;
    @Column(nullable = false, length = 120)
    private String studentName;
    @Column(nullable = false, unique = true, length = 150)
    private String email;
    @Column(nullable = false, length = 80)
    private String department;
    @Column(nullable = false, length = 10)
    private String phoneNumber;

    public Student() {}
    public Long getStudentId() { return studentId; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}
