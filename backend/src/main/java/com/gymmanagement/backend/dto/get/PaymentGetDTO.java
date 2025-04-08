package com.gymmanagement.backend.dto.get;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PaymentGetDTO {
    private Long id;
    private CustomerGetDTO customer;
    private Double amount;
    private LocalDate paymentDate;
    private boolean paid;
}
