package com.gymmanagement.backend.dto.get;

import com.gymmanagement.backend.model.StaffMember;
import lombok.Data;

import java.time.LocalTime;

@Data
public class StaffMemberGetDTO {
    private UserGetDTO user;
    private String address;
    private LocalTime startTime;
    private LocalTime endTime;
    private StaffMember.StaffType staffType;
}
