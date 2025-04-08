package com.gymmanagement.backend.service.impl;

import com.gymmanagement.backend.dto.get.EnrollmentGetDTO;
import com.gymmanagement.backend.dto.post.EnrollmentPostDTO;
import com.gymmanagement.backend.dto.put.EnrollmentPutDTO;
import com.gymmanagement.backend.mappers.EnrollmentMapper;
import com.gymmanagement.backend.model.Customer;
import com.gymmanagement.backend.model.Enrollment;
import com.gymmanagement.backend.model.FitnessClass;
import com.gymmanagement.backend.repository.CustomerRepository;
import com.gymmanagement.backend.repository.EnrollmentRepository;
import com.gymmanagement.backend.repository.FitnessClassRepository;
import com.gymmanagement.backend.service.interfaces.EnrollmentService;
import com.gymmanagement.backend.service.interfaces.FitnessClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CustomerRepository customerRepository;
    private final FitnessClassRepository fitnessClassRepository;
    private final FitnessClassService fitnessClassService;
    private final EnrollmentMapper enrollmentMapper;

    @Override
    public List<EnrollmentGetDTO> findAllEnrollments() {
        return enrollmentRepository.findAll()
                .stream()
                .map(enrollmentMapper::mapEnrollmentEntityToGetDto)
                .collect(Collectors.toList());
    }

    @Override
    public EnrollmentGetDTO findEnrollmentById(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found with ID: " + id));
        return enrollmentMapper.mapEnrollmentEntityToGetDto(enrollment);
    }

    @Override
    public EnrollmentGetDTO saveEnrollment(EnrollmentPostDTO dto) {
        // Validate related entities
        Customer customer = customerRepository.findById(dto.getCustomerId()).orElseThrow();
        FitnessClass fitnessClass = fitnessClassRepository.findById(dto.getClassId()).orElseThrow();

        if (!fitnessClass.isActive()) {
            throw new RuntimeException("Cannot enroll in an inactive class.");
        }

        if (!fitnessClassService.hasAvailableSpots(fitnessClass.getId())) {
            throw new RuntimeException("No spots available in this class.");
        }

        if (enrollmentRepository.existsByCustomerIdAndFitnessClassId(customer.getId(), fitnessClass.getId())) {
            throw new RuntimeException("Client is already enrolled in this class.");
        }

        Enrollment enrollment = enrollmentMapper.mapPostDtoToEnrollmentEntity(dto);
        enrollment.setCustomer(customer);
        enrollment.setFitnessClass(fitnessClass);

        Enrollment saved = enrollmentRepository.save(enrollment);
        return enrollmentMapper.mapEnrollmentEntityToGetDto(saved);
    }

    @Override
    public EnrollmentGetDTO updateEnrollment(Long id, EnrollmentPutDTO dto) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found with ID: " + id));

        if (dto.getCustomerId() != null) {
            Customer customer = customerRepository.findById(dto.getCustomerId()).orElseThrow();
            enrollment.setCustomer(customer);
        }

        if (dto.getClassId() != null) {
            FitnessClass fitnessClass = fitnessClassRepository.findById(dto.getClassId()).orElseThrow();
            enrollment.setFitnessClass(fitnessClass);
        }

        enrollmentMapper.updateEnrollmentEntityFromPutDto(dto, enrollment);

        Enrollment updated = enrollmentRepository.save(enrollment);
        return enrollmentMapper.mapEnrollmentEntityToGetDto(updated);
    }

    @Override
    public void deleteEnrollment(Long id) {
        if (!enrollmentRepository.existsById(id)) {
            throw new RuntimeException("Enrollment not found with ID: " + id);
        }
        enrollmentRepository.deleteById(id);
    }

    @Override
    public List<EnrollmentGetDTO> findByCustomer(Long clientId) {
        return enrollmentRepository.findByCustomerId(clientId)
                .stream()
                .map(enrollmentMapper::mapEnrollmentEntityToGetDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EnrollmentGetDTO> findByClass(Long classId) {
        return enrollmentRepository.findByFitnessClassId(classId)
                .stream()
                .map(enrollmentMapper::mapEnrollmentEntityToGetDto)
                .collect(Collectors.toList());
    }

    @Override
    public void registerAttendance(Long enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found with ID: " + enrollmentId));

        enrollment.setAttended(true);
        enrollmentRepository.save(enrollment);
    }
}
