package com.gymmanagement.backend.dto.post;

import com.gymmanagement.backend.model.StaffMember;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalTime;

@Data
public class StaffMemberPostDTO {

    @NotNull
    private UserPostDTO user;

    @NotBlank
    private String address;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    @NotNull
    private StaffMember.StaffType staffType;
}
