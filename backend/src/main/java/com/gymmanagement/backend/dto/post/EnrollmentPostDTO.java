package com.gymmanagement.backend.dto.post;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class EnrollmentPostDTO {

    @NotNull
    private Long customerId;

    @NotNull
    private Long classId;

    private boolean attended = false;
}
