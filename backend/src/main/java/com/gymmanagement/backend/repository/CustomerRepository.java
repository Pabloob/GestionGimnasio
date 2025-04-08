package com.gymmanagement.backend.repository;

import com.gymmanagement.backend.model.Customer;
import com.gymmanagement.backend.model.Enrollment;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);

    @Query("SELECT e FROM Enrollment e JOIN FETCH e.fitnessClass WHERE e.customer.id = :customerId")
    List<Enrollment> findEnrollmentsByCustomerIdWithClass(@Param("customerId") Long customerId);

    boolean existsByEmail(@NotBlank @Email String email);
}
