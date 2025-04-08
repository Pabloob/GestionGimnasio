package com.gymmanagement.backend.dto.get;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ScheduleGetDTO {
    private Long id;
    private FitnessClassGetDTO fitnessClass;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private RoomGetDTO room;
    private StaffMemberGetDTO instructor;
    private LocalDate startDate;
    private LocalDate endDate;
}
