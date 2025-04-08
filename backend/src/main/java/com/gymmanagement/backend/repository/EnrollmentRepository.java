package com.gymmanagement.backend.repository;

import com.gymmanagement.backend.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByCustomerId(Long customerId);

    List<Enrollment> findByFitnessClassId(Long fitnessClassId);

    boolean existsByCustomerIdAndFitnessClassId(Long customerId, Long fitnessClassId);

}
