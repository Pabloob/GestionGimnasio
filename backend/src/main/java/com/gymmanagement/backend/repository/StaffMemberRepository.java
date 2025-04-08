package com.gymmanagement.backend.repository;

import com.gymmanagement.backend.model.StaffMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffMemberRepository extends JpaRepository<StaffMember, Long> {

    List<StaffMember> findByStaffRole(StaffMember.StaffRole staffType);

    boolean existsByEmail(String email);

    Optional<StaffMember> findByEmail(String email);
}
