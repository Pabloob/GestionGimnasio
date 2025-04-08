package com.gymmanagement.backend.dto.post;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PaymentPostDTO {

    @NotNull
    private Long customerId;

    @NotNull
    @Positive
    private Double amount;

    private boolean paid = false;
}
