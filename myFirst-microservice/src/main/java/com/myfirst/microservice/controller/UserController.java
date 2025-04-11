/**
 * Copyright (c) 2025 MyFirst Microservice
 *
 * REST Controller for managing User entities.
 * Provides endpoints for creating, updating, retrieving, deleting,
 * and partially updating user data, including paginated access.
 *
 * API documentation and parameter descriptions are included via OpenAPI annotations.
 *
 * @author ChatGPT
 * @created 2025-04-10
 */
package com.myfirst.microservice.controller;

import com.myfirst.microservice.dto.UserDTO;
import com.myfirst.microservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User API", description = "API for managing application users")
public class UserController {

    @Autowired
    private UserService service;

    /**
     * Creates a new user.
     *
     * @param dto User data transfer object
     * @return Created UserDTO
     */
    @Operation(summary = "Create a new user", description = "Adds a new user with role assignments")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User created successfully",
            content = @Content(schema = @Schema(implementation = UserDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid user input"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    /**
     * Updates an existing user by ID.
     *
     * @param id User ID
     * @param dto Updated user data
     * @return Updated UserDTO
     */
    @Operation(summary = "Update user", description = "Updates existing user identified by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User updated successfully",
            content = @Content(schema = @Schema(implementation = UserDTO.class))),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(
        @Parameter(description = "ID of the user to update") @PathVariable Long id,
        @RequestBody UserDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id User ID
     * @return UserDTO
     */
    @Operation(summary = "Get user by ID", description = "Retrieves user details by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User retrieved successfully",
            content = @Content(schema = @Schema(implementation = UserDTO.class))),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(
        @Parameter(description = "ID of the user to retrieve") @PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    /**
     * Retrieves all users.
     *
     * @return List of UserDTO
     */
    @Operation(summary = "List all users", description = "Retrieves all users without pagination")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Users retrieved successfully",
            content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    /**
     * Retrieves users with pagination support.
     *
     * @param pageable Pageable parameters (page, size, sort)
     * @return Page of UserDTO
     */
    @Operation(summary = "List paged users", description = "Retrieves users with pagination")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Paged users retrieved successfully",
            content = @Content(schema = @Schema(implementation = Page.class)))
    })
    @GetMapping("/paged")
    public ResponseEntity<Page<UserDTO>> getAllPaged(Pageable pageable) {
        return ResponseEntity.ok(service.getAllPaged(pageable));
    }

    /**
     * Deletes a user by ID.
     *
     * @param id User ID
     * @return No content response if deletion is successful
     */
    @Operation(summary = "Delete user", description = "Deletes the user identified by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User deleted successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @Parameter(description = "ID of the user to delete") @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Partially updates user fields.
     *
     * @param id User ID
     * @param updates Map of fields and values to update
     * @return Updated UserDTO
     */
    @Operation(summary = "Patch user", description = "Partially updates a user's fields")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User patched successfully",
            content = @Content(schema = @Schema(implementation = UserDTO.class))),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> patch(
        @Parameter(description = "ID of the user to patch") @PathVariable Long id,
        @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(service.patch(id, updates));
    }
}
