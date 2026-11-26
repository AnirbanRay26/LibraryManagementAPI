package com.anirban.librarymanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record StudentRequest(
        @NotBlank(message = "Student name is required") String studentName,
        @NotBlank(message = "Email is required") @Email(message = "Enter a valid email address") String email,
        @NotBlank(message = "Department is required") String department,
        @NotBlank(message = "Phone number is required") @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must contain exactly 10 digits") String phoneNumber
) {}
