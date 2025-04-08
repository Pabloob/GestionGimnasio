package com.gymmanagement.backend.dto.get;

import lombok.Data;

@Data
public class FitnessClassGetDTO {
    private Long id;
    private String name;
    private Integer maxCapacity;
    private Double price;
    private String description;
    private boolean active;
}
