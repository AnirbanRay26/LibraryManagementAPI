package com.anirban.librarymanagement.controller;

import com.anirban.librarymanagement.dto.StudentRequest;
import com.anirban.librarymanagement.dto.StudentResponse;
import com.anirban.librarymanagement.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService service;
    public StudentController(StudentService service) { this.service = service; }
    @PostMapping public ResponseEntity<StudentResponse> register(@Valid @RequestBody StudentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.register(request));
    }
    @GetMapping public List<StudentResponse> all() { return service.all(); }
    @GetMapping("/{id}") public StudentResponse byId(@PathVariable Long id) { return service.byId(id); }
    @PutMapping("/{id}") public StudentResponse update(@PathVariable Long id, @Valid @RequestBody StudentRequest request) {
        return service.update(id, request);
    }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id); return ResponseEntity.noContent().build();
    }
}
