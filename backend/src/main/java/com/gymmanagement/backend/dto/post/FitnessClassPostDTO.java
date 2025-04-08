package com.gymmanagement.backend.dto.post;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class FitnessClassPostDTO {

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotNull
    @Positive
    private Integer maxCapacity;

    @NotNull
    @Positive
    private Double price;

    @NotBlank
    private String description;

    private boolean active = true;

}
