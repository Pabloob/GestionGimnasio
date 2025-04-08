package com.gymmanagement.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "enrollments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"customer", "fitnessClass"})
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private FitnessClass fitnessClass;

    @NotNull
    private LocalDate registrationDate;

    private boolean attended = false;

    @PrePersist
    private void setRegistrationDate() {
        if (registrationDate == null) {
            registrationDate = LocalDate.now();
        }
    }
}
