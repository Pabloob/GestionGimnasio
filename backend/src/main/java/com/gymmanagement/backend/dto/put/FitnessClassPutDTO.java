package com.gymmanagement.backend.dto.put;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class FitnessClassPutDTO {
    @Size(max = 100)
    private String name;

    @Positive
    private Integer maxCapacity;

    @Positive
    private Double price;

    private String description;

    private boolean active = true;
}
