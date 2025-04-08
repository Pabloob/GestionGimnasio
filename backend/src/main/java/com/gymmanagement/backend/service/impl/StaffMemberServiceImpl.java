package com.gymmanagement.backend.service.impl;

import com.gymmanagement.backend.dto.get.StaffMemberGetDTO;
import com.gymmanagement.backend.dto.post.StaffMemberPostDTO;
import com.gymmanagement.backend.dto.put.StaffMemberPutDTO;
import com.gymmanagement.backend.mappers.StaffMemberMapper;
import com.gymmanagement.backend.model.StaffMember;
import com.gymmanagement.backend.model.User;
import com.gymmanagement.backend.repository.ScheduleRepository;
import com.gymmanagement.backend.repository.StaffMemberRepository;
import com.gymmanagement.backend.service.interfaces.StaffMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StaffMemberServiceImpl implements StaffMemberService {

    private final StaffMemberRepository staffMemberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ScheduleRepository scheduleRepository;
    private final StaffMemberMapper staffMemberMapper;

    @Override
    public List<StaffMemberGetDTO> findAllStaffMembers() {
        return staffMemberRepository.findAll()
                .stream()
                .map(staffMemberMapper::mapStaffEntityToGetDto)
                .collect(Collectors.toList());
    }

    @Override
    public StaffMemberGetDTO findStaffMemberById(Long id) {
        StaffMember staffMember = staffMemberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff member not found with ID: " + id));
        return staffMemberMapper.mapStaffEntityToGetDto(staffMember);
    }

    @Override
    public StaffMemberGetDTO saveStaffMember(StaffMemberPostDTO dto) {

        if (staffMemberRepository.existsByEmail(dto.getUser().getEmail())) {
            throw new RuntimeException("The email is already registered.");
        }

        if (dto.getEndTime().isBefore(dto.getStartTime())) {
            throw new RuntimeException("End time must be after start time.");
        }

        dto.getUser().setPassword(passwordEncoder.encode(dto.getUser().getPassword()));

        StaffMember staffMember = staffMemberMapper.mapPostDtoToStaffEntity(dto);
        staffMember.setUserType(User.UserType.STAFF);

        StaffMember saved = staffMemberRepository.save(staffMember);
        return staffMemberMapper.mapStaffEntityToGetDto(saved);
    }

    @Override
    public StaffMemberGetDTO updateStaffMember(Long id, StaffMemberPutDTO dto) {
        StaffMember staffMember = staffMemberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff member not found with ID: " + id));

        if (dto.getUserPutDTO().getEmail() != null &&
                !dto.getUserPutDTO().getEmail().equals(staffMember.getEmail()) &&
                staffMemberRepository.existsByEmail(dto.getUserPutDTO().getEmail())) {
            throw new RuntimeException("The new email is already registered.");
        }

        if (dto.getEndTime() != null && dto.getStartTime() != null &&
                dto.getEndTime().isBefore(dto.getStartTime())) {
            throw new RuntimeException("End time must be after start time.");
        }

        if (dto.getUserPutDTO().getPassword() != null && !dto.getUserPutDTO().getPassword().isEmpty()) {
            dto.getUserPutDTO().setPassword(passwordEncoder.encode(dto.getUserPutDTO().getPassword()));
        }

        staffMemberMapper.updateStaffEntityFromPutDto(dto, staffMember);

        StaffMember updated = staffMemberRepository.save(staffMember);
        return staffMemberMapper.mapStaffEntityToGetDto(updated);
    }

    @Override
    public void deleteStaffMember(Long id) {
        StaffMember staffMember = staffMemberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff member not found with ID: " + id));

        if (scheduleRepository.existsByInstructorId(id)) {
            throw new RuntimeException("Cannot delete a staff member with assigned bookings.");
        }

        staffMemberRepository.delete(staffMember);
    }

    @Override
    public List<StaffMemberGetDTO> findByType(String type) {
        try {
            StaffMember.StaffType staffType = StaffMember.StaffType.valueOf(type.toUpperCase());
            return staffMemberRepository.findByStaffType(staffType)
                    .stream()
                    .map(staffMemberMapper::mapStaffEntityToGetDto)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid staff member type: " + type);
        }
    }

    @Override
    public boolean isAvailable(Long staffMemberId, DayOfWeek day, LocalTime startTime, LocalTime endTime) {
        StaffMember staffMember = staffMemberRepository.findById(staffMemberId)
                .orElseThrow(() -> new RuntimeException("Staff member not found with ID: " + staffMemberId));

        boolean withinWorkHours = !startTime.isBefore(staffMember.getStartTime()) &&
                !endTime.isAfter(staffMember.getEndTime());

        boolean noConflictingSchedules = scheduleRepository
                .findByInstructorIdAndDayOfWeek(staffMemberId, day)
                .stream()
                .noneMatch(schedule -> timesOverlap(
                        schedule.getStartTime(),
                        schedule.getEndTime(),
                        startTime,
                        endTime
                ));

        return withinWorkHours && noConflictingSchedules;
    }

    private boolean timesOverlap(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        return start1.isBefore(end2) && end1.isAfter(start2);
    }
}
