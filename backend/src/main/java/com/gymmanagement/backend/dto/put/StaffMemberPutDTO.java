package com.gymmanagement.backend.dto.put;

import com.gymmanagement.backend.model.StaffMember;
import lombok.Data;

import java.time.LocalTime;

@Data
public class StaffMemberPutDTO {

    private UserPutDTO userPutDTO;

    private String address;

    private LocalTime startTime;

    private LocalTime endTime;

    private StaffMember.StaffType staffType;
}
