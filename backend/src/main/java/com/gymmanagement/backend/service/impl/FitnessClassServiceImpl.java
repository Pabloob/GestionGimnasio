package com.gymmanagement.backend.service.impl;

import com.gymmanagement.backend.dto.get.FitnessClassGetDTO;
import com.gymmanagement.backend.dto.post.FitnessClassPostDTO;
import com.gymmanagement.backend.dto.put.FitnessClassPutDTO;
import com.gymmanagement.backend.mappers.FitnessClassMapper;
import com.gymmanagement.backend.model.FitnessClass;
import com.gymmanagement.backend.repository.FitnessClassRepository;
import com.gymmanagement.backend.repository.EnrollmentRepository;
import com.gymmanagement.backend.service.interfaces.FitnessClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class FitnessClassServiceImpl implements FitnessClassService {

    private final FitnessClassRepository fitnessClassRepository;
    private final FitnessClassMapper fitnessClassMapper;
    private final EnrollmentRepository enrollmentRepository;

    @Override
    public List<FitnessClassGetDTO> findAllClasses() {
        return fitnessClassRepository.findAll()
                .stream()
                .map(fitnessClassMapper::mapFitnessClassEntityToGetDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<FitnessClassGetDTO> findActiveClasses() {
        return fitnessClassRepository.findByActiveTrue()
                .stream()
                .map(fitnessClassMapper::mapFitnessClassEntityToGetDto)
                .collect(Collectors.toList());
    }

    @Override
    public FitnessClassGetDTO findClassById(Long id) {
        FitnessClass fitnessClass = fitnessClassRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fitness class not found with ID: " + id));
        return fitnessClassMapper.mapFitnessClassEntityToGetDto(fitnessClass);
    }

    @Override
    public FitnessClassGetDTO saveClass(FitnessClassPostDTO fitnessClassPostDTO) {
        FitnessClass fitnessClass = fitnessClassMapper.mapPostDtoToFitnessClassEntity(fitnessClassPostDTO);
        FitnessClass savedClass = fitnessClassRepository.save(fitnessClass);
        return fitnessClassMapper.mapFitnessClassEntityToGetDto(savedClass);
    }

    @Override
    public FitnessClassGetDTO updateClass(Long id, FitnessClassPutDTO fitnessClassPutDTO) {
        FitnessClass fitnessClass = fitnessClassRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fitness class not found with ID: " + id));

        fitnessClassMapper.updateFitnessClassEntityFromPutDto(fitnessClassPutDTO, fitnessClass);
        FitnessClass updatedClass = fitnessClassRepository.save(fitnessClass);
        return fitnessClassMapper.mapFitnessClassEntityToGetDto(updatedClass);
    }

    @Override
    public void toggleClassStatus(Long id) {
        FitnessClass fitnessClass = fitnessClassRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fitness class not found with ID: " + id));

        fitnessClass.setActive(!fitnessClass.isActive());
        fitnessClassRepository.save(fitnessClass);
    }

    @Override
    public boolean hasAvailableSpots(Long classId) {
        FitnessClass fitnessClass = fitnessClassRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Fitness class not found with ID: " + classId));

        return enrollmentRepository.findByFitnessClassId(fitnessClass.getId()).size() < fitnessClass.getMaxCapacity();
    }

    @Override
    public int availableSpots(Long classId) {
        FitnessClass fitnessClass = fitnessClassRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Fitness class not found with ID: " + classId));

        return fitnessClass.getMaxCapacity() - enrollmentRepository.findByFitnessClassId(fitnessClass.getId()).size();
    }

    @Override
    public void deleteClass(Long id) {
        if (!fitnessClassRepository.existsById(id)) {
            throw new RuntimeException("Fitness class not found with ID: " + id);
        }
        fitnessClassRepository.deleteById(id);
    }
}
