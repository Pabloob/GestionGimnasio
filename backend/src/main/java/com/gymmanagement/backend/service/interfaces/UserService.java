package com.gymmanagement.backend.service.interfaces;

import com.gymmanagement.backend.dto.UserLoginDTO;
import com.gymmanagement.backend.dto.get.UserGetDTO;

import java.util.List;

public interface UserService {

    Object authenticate(UserLoginDTO loginDTO);

    UserGetDTO findById(Long id);

    UserGetDTO getByEmail(String email);

    List<UserGetDTO> getAll();

    boolean existsByEmail(String email);

    void toggleUserStatus(Long id);
}
