package com.gymmanagement.backend.dto.put;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PaymentPutDTO {
    private Long customerId;

    @Positive
    private Double amount;

    private boolean paid = false;
}
