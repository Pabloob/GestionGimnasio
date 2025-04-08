package com.gymmanagement.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customers")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Customer extends User {

    @Override
    protected void specificValidations() {
        if (getUserType() != UserType.CUSTOMER) {
            throw new IllegalStateException("User type must be CUSTOMER");
        }
    }
}
