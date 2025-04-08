package com.gymmanagement.backend.service.interfaces;

import com.gymmanagement.backend.dto.get.EnrollmentGetDTO;
import com.gymmanagement.backend.dto.post.EnrollmentPostDTO;
import com.gymmanagement.backend.dto.put.EnrollmentPutDTO;

import java.util.List;

public interface EnrollmentService {
    List<EnrollmentGetDTO> findAllEnrollments();
    EnrollmentGetDTO findEnrollmentById(Long id);
    EnrollmentGetDTO saveEnrollment(EnrollmentPostDTO enrollmentPostDTO);
    EnrollmentGetDTO updateEnrollment(Long id, EnrollmentPutDTO enrollmentPutDTO);
    void deleteEnrollment(Long id);
    List<EnrollmentGetDTO> findByCustomer(Long customerId);
    List<EnrollmentGetDTO> findByClass(Long classId);
    void registerAttendance(Long enrollmentId);
}
