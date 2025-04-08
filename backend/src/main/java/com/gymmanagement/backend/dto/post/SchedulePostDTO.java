package com.gymmanagement.backend.dto.post;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class SchedulePostDTO {

    @NotNull
    private Long classId;

    @NotNull
    private DayOfWeek dayOfWeek;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    @NotNull
    private Long roomId;

    @NotNull
    private Long instructorId;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;
}
