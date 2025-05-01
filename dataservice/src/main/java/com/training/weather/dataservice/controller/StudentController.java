package com.training.weather.dataservice.controller;

import com.training.weather.dataservice.model.Student;
import com.training.weather.dataservice.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    
    private final StudentService studentService;
    private final RestTemplate restTemplate = new RestTemplate();
    
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable String id) {
        return studentService.getStudentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/lastname/{lastName}")
    public ResponseEntity<List<Student>> getStudentsByLastName(@PathVariable String lastName) {
        return ResponseEntity.ok(studentService.getStudentsByLastName(lastName));
    }
    
    @GetMapping("/major/{major}")
    public ResponseEntity<List<Student>> getStudentsByMajor(@PathVariable String major) {
        return ResponseEntity.ok(studentService.getStudentsByMajor(major));
    }
    
    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return new ResponseEntity<>(studentService.saveStudent(student), HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable String id, @RequestBody Student student) {
        return studentService.getStudentById(id)
                .map(existingStudent -> {
                    student.setId(id);
                    return ResponseEntity.ok(studentService.saveStudent(student));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String id) {
        return studentService.getStudentById(id)
                .map(student -> {
                    studentService.deleteStudent(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
