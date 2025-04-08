package com.gymmanagement.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gymmanagement.backend.model.FitnessClass;

@Repository
public interface FitnessClassRepository extends JpaRepository<FitnessClass, Long> {

    List<FitnessClass> findByActiveTrue();

}
