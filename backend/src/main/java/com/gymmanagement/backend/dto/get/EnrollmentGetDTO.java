package com.gymmanagement.backend.dto.get;

import lombok.Data;
import java.time.LocalDate;

@Data
public class EnrollmentGetDTO {
    private Long id;
    private CustomerGetDTO customer;
    private FitnessClassGetDTO fitnessClass;
    private LocalDate registrationDate;
    private boolean attended;
}
