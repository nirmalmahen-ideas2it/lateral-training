/**
 * Copyright (c) 2025 MyFirst Microservice
 *
 * Service class for managing User entities.
 * Provides business logic and interaction with the UserRepository and RoleRepository.
 * Handles create, read, update, delete, and patch operations.
 *
 * @author Alagu Nirmal Mahendran
 * @created 2025-04-10
 */
package com.myfirst.microservice.service;

import com.myfirst.microservice.entity.User;
import com.myfirst.microservice.repository.RoleRepository;
import com.myfirst.microservice.repository.UserRepository;
import com.myfirst.microservice.dto.UserDTO;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private ModelMapper mapper;

    /**
     * Creates a new user entity based on the DTO.
     *
     * @param dto UserDTO containing user information and role IDs
     * @return Created UserDTO
     */
    public UserDTO create(UserDTO dto) {
        try {
            User user = mapper.map(dto, User.class);
            user.setRoles(roleRepo.findAllById(dto.getRoleIds()).stream().collect(Collectors.toSet()));
            User saved = userRepo.save(user);
            logger.info("User created with ID: {}", saved.getId());
            return mapper.map(saved, UserDTO.class);
        } catch (Exception e) {
            logger.error("Error creating user", e);
            throw new RuntimeException("Failed to create user", e);
        }
    }

    /**
     * Updates an existing user by ID.
     *
     * @param id User ID
     * @param dto Updated UserDTO
     * @return Updated UserDTO
     */
    public UserDTO update(Long id, UserDTO dto) {
        try {
            User user = userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
            mapper.map(dto, user);
            user.setRoles(roleRepo.findAllById(dto.getRoleIds()).stream().collect(Collectors.toSet()));
            User saved = userRepo.save(user);
            logger.info("User updated with ID: {}", saved.getId());
            return mapper.map(saved, UserDTO.class);
        } catch (Exception e) {
            logger.error("Error updating user with ID: {}", id, e);
            throw new RuntimeException("Failed to update user", e);
        }
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id User ID
     * @return UserDTO
     */
    public UserDTO getById(Long id) {
        try {
            return userRepo.findById(id)
                .map(u -> mapper.map(u, UserDTO.class))
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
        } catch (Exception e) {
            logger.error("Error fetching user with ID: {}", id, e);
            throw e;
        }
    }

    /**
     * Retrieves all users.
     *
     * @return List of UserDTO
     */
    public List<UserDTO> getAll() {
        try {
            return userRepo.findAll().stream()
                .map(u -> mapper.map(u, UserDTO.class))
                .toList();
        } catch (Exception e) {
            logger.error("Error fetching all users", e);
            throw new RuntimeException("Failed to fetch users", e);
        }
    }

    /**
     * Retrieves users with pagination.
     *
     * @param pageable Pagination information
     * @return Page of UserDTO
     */
    public Page<UserDTO> getAllPaged(Pageable pageable) {
        try {
            return userRepo.findAll(pageable).map(u -> mapper.map(u, UserDTO.class));
        } catch (Exception e) {
            logger.error("Error fetching paged users", e);
            throw new RuntimeException("Failed to fetch paged users", e);
        }
    }

    /**
     * Deletes a user by ID.
     *
     * @param id User ID
     */
    public void delete(Long id) {
        try {
            userRepo.deleteById(id);
            logger.info("User deleted with ID: {}", id);
        } catch (Exception e) {
            logger.error("Error deleting user with ID: {}", id, e);
            throw new RuntimeException("Failed to delete user", e);
        }
    }

    /**
     * Partially updates user fields.
     *
     * @param id User ID
     * @param updates Map of field updates
     * @return Updated UserDTO
     */
    public UserDTO patch(Long id, Map<String, Object> updates) {
        try {
            User user = userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
            updates.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(User.class, key);
                if (field != null) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, user, value);
                }
            });
            User saved = userRepo.save(user);
            logger.info("User patched with ID: {}", saved.getId());
            return mapper.map(saved, UserDTO.class);
        } catch (Exception e) {
            logger.error("Error patching user with ID: {}", id, e);
            throw new RuntimeException("Failed to patch user", e);
        }
    }
}
