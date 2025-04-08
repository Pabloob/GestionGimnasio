package com.gymmanagement.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gymmanagement.backend.model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    boolean existsByName(String name);
}
