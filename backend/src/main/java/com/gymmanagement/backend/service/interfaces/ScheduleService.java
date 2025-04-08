package com.gymmanagement.backend.service.interfaces;

import com.gymmanagement.backend.dto.get.ScheduleGetDTO;
import com.gymmanagement.backend.dto.post.SchedulePostDTO;
import com.gymmanagement.backend.dto.put.SchedulePutDTO;

import java.time.DayOfWeek;
import java.util.List;

public interface ScheduleService {
    List<ScheduleGetDTO> findAll();
    ScheduleGetDTO findById(Long id);
    ScheduleGetDTO create(SchedulePostDTO schedulePostDTO);
    ScheduleGetDTO update(Long id, SchedulePutDTO schedulePutDTO);
    void delete(Long id);
    List<ScheduleGetDTO> findByClass(Long classId);
    List<ScheduleGetDTO> findByRoom(Long roomId);
    List<ScheduleGetDTO> findByInstructor(Long instructorId);
    List<ScheduleGetDTO> findByDay(DayOfWeek day);
}
