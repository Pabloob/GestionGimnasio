package com.gymmanagement.backend.dto.put;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class SchedulePutDTO {
    private Long classId;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private Long roomId;
    private Long instructorId;
    private LocalDate startDate;
    private LocalDate endDate;
}
