package com.gymmanagement.backend.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "Password cannot be empty")
    private String password;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    @Pattern(regexp = "^[0-9]{9}$")
    private String phone;

    @NotNull
    @Past
    private LocalDate birthDate;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime registrationDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserType userType;

    private boolean active = true;

    public enum UserType {
        CUSTOMER, STAFF
    }

    @PrePersist
    @PreUpdate
    protected final void validateUser() {
        validateUserType();
        specificValidations();
    }

    private void validateUserType() {
        if (userType == null) {
            throw new IllegalStateException("User type cannot be null");
        }
    }

    protected abstract void specificValidations();
}
