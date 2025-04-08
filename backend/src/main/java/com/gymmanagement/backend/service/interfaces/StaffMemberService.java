package com.gymmanagement.backend.service.interfaces;

import com.gymmanagement.backend.dto.get.StaffMemberGetDTO;
import com.gymmanagement.backend.dto.post.StaffMemberPostDTO;
import com.gymmanagement.backend.dto.put.StaffMemberPutDTO;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public interface StaffMemberService {
    List<StaffMemberGetDTO> findAllStaffMembers();
    StaffMemberGetDTO findStaffMemberById(Long id);
    StaffMemberGetDTO saveStaffMember(StaffMemberPostDTO staffMemberPostDTO);
    StaffMemberGetDTO updateStaffMember(Long id, StaffMemberPutDTO staffMemberPutDTO);
    void deleteStaffMember(Long id);
    List<StaffMemberGetDTO> findByType(String staffType);
    boolean isAvailable(Long staffMemberId, DayOfWeek day, LocalTime startTime, LocalTime endTime);
}
