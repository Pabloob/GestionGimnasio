package com.gymmanagement.backend.model;

import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "staff_members")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class StaffMember extends User {

    @NotBlank
    private String address;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StaffMember.StaffType staffType;

    public enum StaffType {
        ADMIN, INSTRUCTOR, RECEPTIONIST
    }

    @Override
    protected void specificValidations() {
        if (getUserType() != UserType.STAFF) {
            throw new IllegalStateException("User type must be STAFF");
        }
        if (endTime.isBefore(startTime)) {
            throw new IllegalStateException("End time must be after start time");
        }
    }
}
