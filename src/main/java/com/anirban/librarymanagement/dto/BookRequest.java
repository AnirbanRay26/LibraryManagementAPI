package com.anirban.librarymanagement.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookRequest(
        @NotBlank(message = "Title is required") String title,
        @NotBlank(message = "Author is required") String author,
        @NotBlank(message = "ISBN is required") String isbn,
        @NotBlank(message = "Category is required") String category,
        @NotNull(message = "Total copies is required") @Min(value = 1, message = "At least one copy is required") Integer totalCopies
) {}
