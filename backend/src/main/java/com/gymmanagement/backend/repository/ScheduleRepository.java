package com.gymmanagement.backend.repository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import com.gymmanagement.backend.model.Room;
import com.gymmanagement.backend.model.Schedule;
import com.gymmanagement.backend.model.StaffMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gymmanagement.backend.model.FitnessClass;

import jakarta.validation.constraints.NotNull;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    boolean existsByInstructorId(Long id);
    List<Schedule> findByInstructorId(Long instructorId);
    boolean existsByFitnessClassAndRoomAndInstructorAndDayOfWeekAndStartTimeAndEndTime(FitnessClass fitnessClass, Room room, StaffMember instructor, @NotNull DayOfWeek dayOfWeek, @NotNull LocalTime startTime, @NotNull LocalTime endTime);

    List<Schedule> findByFitnessClassId(Long fitnessClassId);

    List<Schedule> findByRoomId(Long roomId);

    List<Schedule> findByDayOfWeek(DayOfWeek day);

    boolean existsByDayOfWeekAndRoomIdAndStartTimeLessThanAndEndTimeGreaterThanAndIdNot(DayOfWeek day, Long roomId, LocalTime endTime, LocalTime startTime, Long excludeId);

    boolean existsByDayOfWeekAndInstructorIdAndStartTimeLessThanAndEndTimeGreaterThanAndIdNot(DayOfWeek day, Long instructorId, LocalTime endTime, LocalTime startTime, Long excludeId);

    boolean existsByRoomId(Long id);

    List<Schedule> findByInstructorIdAndDayOfWeek(Long staffMemberId, DayOfWeek day);
}