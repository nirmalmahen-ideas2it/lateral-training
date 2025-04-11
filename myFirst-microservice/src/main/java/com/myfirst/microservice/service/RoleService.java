/**
 * Copyright (c) 2025 MyFirst Microservice
 *
 * Service class for managing Role entities.
 * Handles business logic for creating, updating, retrieving,
 * deleting, and patching Role records.
 *
 * @author Alagu Nirmal Mahendran
 * @created 2025-04-10
 */
package com.myfirst.microservice.service;

import com.myfirst.microservice.dto.RoleDTO;
import com.myfirst.microservice.entity.Role;
import com.myfirst.microservice.repository.RoleRepository;
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
import java.util.NoSuchElementException;

@Service
public class RoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private ModelMapper mapper;

    /**
     * Creates a new Role from DTO.
     *
     * @param dto Role data
     * @return Saved RoleDTO
     */
    public RoleDTO create(RoleDTO dto) {
        try {
            Role role = mapper.map(dto, Role.class);
            Role saved = roleRepo.save(role);
            logger.info("Created new role with ID {}", saved.getId());
            return mapper.map(saved, RoleDTO.class);
        } catch (Exception e) {
            logger.error("Error creating role: {}", e.getMessage(), e);
            throw new IllegalArgumentException("Failed to create role", e);
        }
    }

    /**
     * Updates an existing Role by ID.
     *
     * @param id Role ID
     * @param dto Updated Role data
     * @return Updated RoleDTO
     */
    public RoleDTO update(Long id, RoleDTO dto) {
        try {
            Role role = roleRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Role not found"));
            mapper.typeMap(RoleDTO.class, Role.class).addMappings(m -> m.skip(Role::setId));
            mapper.map(dto, role);
            Role updated = roleRepo.save(role);
            logger.info("Updated role with ID {}", id);
            return mapper.map(updated, RoleDTO.class);
        } catch (NoSuchElementException e) {
            logger.warn("Role ID {} not found for update", id);
            throw e;
        } catch (Exception e) {
            logger.error("Error updating role ID {}: {}", id, e.getMessage(), e);
            throw new IllegalArgumentException("Failed to update role", e);
        }
    }

    /**
     * Gets a Role by ID.
     *
     * @param id Role ID
     * @return RoleDTO
     */
    public RoleDTO getById(Long id) {
        return roleRepo.findById(id)
            .map(r -> mapper.map(r, RoleDTO.class))
            .orElseThrow(() -> {
                logger.warn("Role ID {} not found", id);
                return new NoSuchElementException("Role not found");
            });
    }

    /**
     * Returns all roles.
     *
     * @return List of RoleDTO
     */
    public List<RoleDTO> getAll() {
        return roleRepo.findAll().stream()
            .map(r -> mapper.map(r, RoleDTO.class))
            .toList();
    }

    /**
     * Returns roles in paged format.
     *
     * @param pageable Pagination object
     * @return Page of RoleDTO
     */
    public Page<RoleDTO> getAllPaged(Pageable pageable) {
        return roleRepo.findAll(pageable)
            .map(r -> mapper.map(r, RoleDTO.class));
    }

    /**
     * Deletes a Role by ID.
     *
     * @param id Role ID
     */
    public void delete(Long id) {
        try {
            roleRepo.deleteById(id);
            logger.info("Deleted role with ID {}", id);
        } catch (Exception e) {
            logger.error("Error deleting role ID {}: {}", id, e.getMessage(), e);
            throw new IllegalArgumentException("Failed to delete role", e);
        }
    }

    /**
     * Applies partial update to a Role entity.
     *
     * @param id Role ID
     * @param updates Map of fields to update
     * @return Updated RoleDTO
     */
    public RoleDTO patch(Long id, Map<String, Object> updates) {
        try {
            Role role = roleRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Role not found"));
            updates.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Role.class, key);
                if (field != null) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, role, value);
                } else {
                    logger.warn("Field {} does not exist on Role class", key);
                }
            });
            Role updated = roleRepo.save(role);
            logger.info("Patched role with ID {}", id);
            return mapper.map(updated, RoleDTO.class);
        } catch (Exception e) {
            logger.error("Error patching role ID {}: {}", id, e.getMessage(), e);
            throw new IllegalArgumentException("Failed to patch role", e);
        }
    }
}
