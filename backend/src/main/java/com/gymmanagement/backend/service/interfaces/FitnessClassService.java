package com.gymmanagement.backend.service.interfaces;

import com.gymmanagement.backend.dto.get.FitnessClassGetDTO;
import com.gymmanagement.backend.dto.post.FitnessClassPostDTO;
import com.gymmanagement.backend.dto.put.FitnessClassPutDTO;

import java.util.List;

public interface FitnessClassService {
    List<FitnessClassGetDTO> findAllClasses();
    List<FitnessClassGetDTO> findActiveClasses();
    FitnessClassGetDTO findClassById(Long id);
    FitnessClassGetDTO saveClass(FitnessClassPostDTO fitnessClassPostDTO);
    FitnessClassGetDTO updateClass(Long id, FitnessClassPutDTO fitnessClassPutDTO);
    void toggleClassStatus(Long id);
    boolean hasAvailableSpots(Long classId);
    int availableSpots(Long classId);
    void deleteClass(Long id);
}
