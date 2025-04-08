package com.gymmanagement.backend.service.impl;

import com.gymmanagement.backend.dto.get.ScheduleGetDTO;
import com.gymmanagement.backend.dto.post.SchedulePostDTO;
import com.gymmanagement.backend.dto.put.SchedulePutDTO;
import com.gymmanagement.backend.mappers.ScheduleMapper;
import com.gymmanagement.backend.model.FitnessClass;
import com.gymmanagement.backend.model.Room;
import com.gymmanagement.backend.model.Schedule;
import com.gymmanagement.backend.model.StaffMember;
import com.gymmanagement.backend.repository.ScheduleRepository;
import com.gymmanagement.backend.repository.FitnessClassRepository;
import com.gymmanagement.backend.repository.RoomRepository;
import com.gymmanagement.backend.repository.StaffMemberRepository;
import com.gymmanagement.backend.service.interfaces.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final FitnessClassRepository fitnessClassRepository;
    private final RoomRepository roomRepository;
    private final StaffMemberRepository staffMemberRepository;
    private final ScheduleMapper scheduleMapper;

    @Override
    public List<ScheduleGetDTO> findAll() {
        return scheduleRepository.findAll()
                .stream()
                .map(scheduleMapper::mapScheduleEntityToGetDto)
                .collect(Collectors.toList());
    }

    @Override
    public ScheduleGetDTO findById(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found with ID: " + id));
        return scheduleMapper.mapScheduleEntityToGetDto(schedule);
    }

    @Override
    public ScheduleGetDTO create(SchedulePostDTO dto) {
        FitnessClass fitnessClass = fitnessClassRepository.findById(dto.getClassId()).orElseThrow();
        Room room = roomRepository.findById(dto.getRoomId()).orElseThrow();
        StaffMember instructor = staffMemberRepository.findById(dto.getInstructorId()).orElseThrow();

        boolean exists = scheduleRepository.existsByFitnessClassAndRoomAndInstructorAndDayOfWeekAndStartTimeAndEndTime(
                fitnessClass, room, instructor, dto.getDayOfWeek(), dto.getStartTime(), dto.getEndTime());

        if (exists) {
            throw new IllegalArgumentException("A schedule already exists with the same class, room, instructor, day and time.");
        }

        validateSchedule(dto.getStartTime(), dto.getEndTime());

        validateAvailability(dto.getDayOfWeek(), dto.getStartTime(), dto.getEndTime(),
                dto.getRoomId(), dto.getInstructorId(), null);

        Schedule schedule = scheduleMapper.mapPostDtoToScheduleEntity(dto);
        setRelations(schedule, fitnessClass, room, instructor);

        Schedule savedSchedule = scheduleRepository.save(schedule);
        return scheduleMapper.mapScheduleEntityToGetDto(savedSchedule);
    }

    @Override
    public ScheduleGetDTO update(Long id, SchedulePutDTO dto) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found with ID: " + id));

        FitnessClass fitnessClass = dto.getClassId() != null ?
                fitnessClassRepository.findById(dto.getClassId()).orElseThrow() :
                schedule.getFitnessClass();

        Room room = dto.getRoomId() != null ?
                roomRepository.findById(dto.getRoomId()).orElseThrow() :
                schedule.getRoom();

        StaffMember instructor = dto.getInstructorId() != null ?
                staffMemberRepository.findById(dto.getInstructorId()).orElseThrow() :
                schedule.getInstructor();

        LocalTime startTime = dto.getStartTime() != null ? dto.getStartTime() : schedule.getStartTime();
        LocalTime endTime = dto.getEndTime() != null ? dto.getEndTime() : schedule.getEndTime();
        validateSchedule(startTime, endTime);

        DayOfWeek day = dto.getDayOfWeek() != null ? dto.getDayOfWeek() : schedule.getDayOfWeek();
        Long roomId = dto.getRoomId() != null ? dto.getRoomId() : schedule.getRoom().getId();
        Long instructorId = dto.getInstructorId() != null ? dto.getInstructorId() : schedule.getInstructor().getId();

        validateAvailability(day, startTime, endTime, roomId, instructorId, id);

        scheduleMapper.updateScheduleEntityFromPutDto(dto, schedule);
        setRelations(schedule, fitnessClass, room, instructor);

        Schedule updatedSchedule = scheduleRepository.save(schedule);
        return scheduleMapper.mapScheduleEntityToGetDto(updatedSchedule);
    }

    @Override
    public void delete(Long id) {
        if (!scheduleRepository.existsById(id)) {
            throw new RuntimeException("Schedule not found with ID: " + id);
        }
        scheduleRepository.deleteById(id);
    }

    @Override
    public List<ScheduleGetDTO> findByClass(Long fitnessClassId) {
        return scheduleRepository.findByFitnessClassId(fitnessClassId)
                .stream()
                .map(scheduleMapper::mapScheduleEntityToGetDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleGetDTO> findByRoom(Long roomId) {
        return scheduleRepository.findByRoomId(roomId)
                .stream()
                .map(scheduleMapper::mapScheduleEntityToGetDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleGetDTO> findByInstructor(Long instructorId) {
        return scheduleRepository.findByInstructorId(instructorId)
                .stream()
                .map(scheduleMapper::mapScheduleEntityToGetDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleGetDTO> findByDay(DayOfWeek day) {
        return scheduleRepository.findByDayOfWeek(day)
                .stream()
                .map(scheduleMapper::mapScheduleEntityToGetDto)
                .collect(Collectors.toList());
    }

    private void setRelations(Schedule schedule, FitnessClass fitnessClass, Room room, StaffMember instructor) {
        schedule.setFitnessClass(fitnessClass);
        schedule.setRoom(room);
        schedule.setInstructor(instructor);
    }

    private void validateSchedule(LocalTime startTime, LocalTime endTime) {
        if (endTime.isBefore(startTime) || endTime.equals(startTime)) {
            throw new RuntimeException("End time must be after start time.");
        }
    }

    private void validateAvailability(DayOfWeek day, LocalTime startTime, LocalTime endTime,
                                      Long roomId, Long instructorId, Long excludeId) {
        boolean roomOccupied = scheduleRepository
                .existsByDayOfWeekAndRoomIdAndStartTimeLessThanAndEndTimeGreaterThanAndIdNot(
                        day, roomId, endTime, startTime, excludeId);

        if (roomOccupied) {
            throw new RuntimeException("The room is already booked for the selected time.");
        }

        boolean instructorOccupied = scheduleRepository
                .existsByDayOfWeekAndInstructorIdAndStartTimeLessThanAndEndTimeGreaterThanAndIdNot(
                        day, instructorId, endTime, startTime, excludeId);

        if (instructorOccupied) {
            throw new RuntimeException("The instructor already has a class scheduled at that time.");
        }
    }
}
