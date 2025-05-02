package com.training.weather.dataservice.controller;

import com.training.weather.dataservice.model.Student;
import com.training.weather.dataservice.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
@Tag(name = "Student Management", description = "Operations for managing student data")
public class StudentController {
    
    private final StudentService studentService;
    private final RestTemplate restTemplate = new RestTemplate();
    
    @Operation(summary = "Get all students", description = "Retrieves a list of all students")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of students")
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }
    
    @Operation(summary = "Get student by ID", description = "Retrieves a student by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Student found"),
        @ApiResponse(responseCode = "404", description = "Student not found", 
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(
            @Parameter(description = "ID of the student to retrieve", required = true) 
            @PathVariable String id) {
        return studentService.getStudentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Get students by last name", 
               description = "Retrieves all students with the specified last name")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved students")
    @GetMapping("/lastname/{lastName}")
    public ResponseEntity<List<Student>> getStudentsByLastName(
            @Parameter(description = "Last name to search for", required = true) 
            @PathVariable String lastName) {
        return ResponseEntity.ok(studentService.getStudentsByLastName(lastName));
    }
    
    @Operation(summary = "Get students by major", 
               description = "Retrieves all students enrolled in the specified major")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved students")
    @GetMapping("/major/{major}")
    public ResponseEntity<List<Student>> getStudentsByMajor(
            @Parameter(description = "Major to filter students by", required = true) 
            @PathVariable String major) {
        return ResponseEntity.ok(studentService.getStudentsByMajor(major));
    }
    
    @Operation(summary = "Create a new student", 
               description = "Creates a new student record")
    @ApiResponse(responseCode = "201", description = "Student successfully created")
    @PostMapping
    public ResponseEntity<Student> createStudent(
            @Parameter(description = "Student details to save", required = true) 
            @RequestBody Student student) {
        return new ResponseEntity<>(studentService.saveStudent(student), HttpStatus.CREATED);
    }
    
    @Operation(summary = "Update a student", 
               description = "Updates an existing student record")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Student successfully updated"),
        @ApiResponse(responseCode = "404", description = "Student not found", 
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @Parameter(description = "ID of the student to update", required = true) 
            @PathVariable String id, 
            @Parameter(description = "Updated student details", required = true) 
            @RequestBody Student student) {
        return studentService.getStudentById(id)
                .map(existingStudent -> {
                    student.setId(id);
                    return ResponseEntity.ok(studentService.saveStudent(student));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Delete a student", 
               description = "Deletes a student record")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Student successfully deleted"),
        @ApiResponse(responseCode = "404", description = "Student not found", 
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(
            @Parameter(description = "ID of the student to delete", required = true) 
            @PathVariable String id) {
        return studentService.getStudentById(id)
                .map(student -> {
                    studentService.deleteStudent(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
