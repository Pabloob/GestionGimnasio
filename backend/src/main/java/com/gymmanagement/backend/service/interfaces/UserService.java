package com.gymmanagement.backend.service.interfaces;

import com.gymmanagement.backend.dto.UserLoginDTO;
import com.gymmanagement.backend.dto.get.FitnessClassGetDTO;

import java.util.List;

public interface UserService {

    Object authenticate(UserLoginDTO loginDTO);

    FitnessClassGetDTO findById(Long id);

    FitnessClassGetDTO getByEmail(String email);

    List<FitnessClassGetDTO> getAll();

    boolean existsByEmail(String email);

    void toggleUserStatus(Long id);
}
