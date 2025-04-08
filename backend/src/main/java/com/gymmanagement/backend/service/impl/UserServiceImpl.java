package com.gymmanagement.backend.service.impl;

import com.gymmanagement.backend.dto.UserLoginDTO;
import com.gymmanagement.backend.dto.get.UserGetDTO;
import com.gymmanagement.backend.mappers.UsuarioMapper;
import com.gymmanagement.backend.model.User;
import com.gymmanagement.backend.repository.CustomerRepository;
import com.gymmanagement.backend.repository.StaffMemberRepository;
import com.gymmanagement.backend.repository.UserRepository;
import com.gymmanagement.backend.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CustomerRepository customerService;
    private final StaffMemberRepository staffService;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper userMapper;

    @Override
    public Object authenticate(UserLoginDTO loginDTO) {
        if (loginDTO.getEmail() == null || loginDTO.getPassword() == null) {
            throw new IllegalArgumentException("Email and password are required");
        }

        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }

        if (!user.isActive()) {
            throw new RuntimeException("User is inactive");
        }

        return switch (user.getUserType()) {
            case CUSTOMER -> customerService.findCustomerById(user.getId());
            case STAFF -> staffService.findStaffById(user.getId());
        };
    }

    @Override
    @Transactional(readOnly = true)
    public UserGetDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        return userMapper.toUsuarioGetDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserGetDTO getByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return userMapper.toUsuarioGetDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserGetDTO> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUsuarioGetDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void toggleUserStatus(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        boolean newStatus = !user.isActive();
        user.setActive(newStatus);
        userRepository.save(user);

        System.out.println("User status with ID " + id + " has been changed to " + (newStatus ? "active" : "inactive"));
    }
}
