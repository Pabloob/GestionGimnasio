package com.gymmanagement.backend.controller;

import com.gymmanagement.backend.dto.get.EnrollmentGetDTO;
import com.gymmanagement.backend.dto.post.EnrollmentPostDTO;
import com.gymmanagement.backend.dto.put.EnrollmentPutDTO;
import com.gymmanagement.backend.service.interfaces.EnrollmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public ResponseEntity<List<EnrollmentGetDTO>> getAllEnrollments() {
        List<EnrollmentGetDTO> enrollments = enrollmentService.findAllEnrollments();
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentGetDTO> getEnrollmentById(@PathVariable Long id) {
        EnrollmentGetDTO enrollment = enrollmentService.findEnrollmentById(id);
        return ResponseEntity.ok(enrollment);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<EnrollmentGetDTO>> getEnrollmentsByCustomer(@PathVariable Long customerId) {
        List<EnrollmentGetDTO> enrollments = enrollmentService.findByCustomer(customerId);
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/class/{classId}")
    public ResponseEntity<List<EnrollmentGetDTO>> getEnrollmentsByClass(@PathVariable Long classId) {
        List<EnrollmentGetDTO> enrollments = enrollmentService.findByClass(classId);
        return ResponseEntity.ok(enrollments);
    }

    @PostMapping
    public ResponseEntity<EnrollmentGetDTO> createEnrollment(@RequestBody EnrollmentPostDTO enrollmentPostDTO) {
        EnrollmentGetDTO newEnrollment = enrollmentService.saveEnrollment(enrollmentPostDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEnrollment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnrollmentGetDTO> updateEnrollment(@PathVariable Long id,
                                                             @RequestBody EnrollmentPutDTO enrollmentPutDTO) {
        EnrollmentGetDTO updatedEnrollment = enrollmentService.updateEnrollment(id, enrollmentPutDTO);
        return ResponseEntity.ok(updatedEnrollment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long id) {
        enrollmentService.deleteEnrollment(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/register-attendance")
    public ResponseEntity<Void> registerAttendance(@PathVariable Long id) {
        enrollmentService.registerAttendance(id);
        return ResponseEntity.noContent().build();
    }
}
