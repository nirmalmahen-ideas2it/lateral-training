/**
 * Copyright (c) 2025 MyFirst Microservice
 *
 * This class defines the REST controller for managing Role entities.
 * It provides endpoints to perform CRUD operations and partial updates.
 *
 * API documentation and parameter descriptions are included via OpenAPI annotations.
 *
 * @author Alagu Nirmal Mahendran
 * @created 2025-04-10
 */
package com.myfirst.microservice.controller;

import com.myfirst.microservice.dto.RoleDTO;
import com.myfirst.microservice.service.RoleService;
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
@RequestMapping("/api/roles")
@Tag(name = "Role API", description = "API for Role management")
public class RoleController {

    @Autowired
    private RoleService service;

    @Operation(summary = "Create a new role", description = "Adds a new role entity to the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Role created successfully",
            content = @Content(schema = @Schema(implementation = RoleDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid role input"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<RoleDTO> create(@RequestBody RoleDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Operation(summary = "Update role", description = "Updates an existing role by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Role updated successfully",
            content = @Content(schema = @Schema(implementation = RoleDTO.class))),
        @ApiResponse(responseCode = "404", description = "Role not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> update(
        @Parameter(description = "ID of the role to update") @PathVariable Long id,
        @RequestBody RoleDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Get role by ID", description = "Returns the role data for a specific ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Role retrieved successfully",
            content = @Content(schema = @Schema(implementation = RoleDTO.class))),
        @ApiResponse(responseCode = "404", description = "Role not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getById(
        @Parameter(description = "ID of the role to retrieve") @PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "List all roles", description = "Returns all roles in the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Roles retrieved successfully",
            content = @Content(schema = @Schema(implementation = RoleDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "List paged roles", description = "Returns roles with pagination support")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Paged roles retrieved successfully",
            content = @Content(schema = @Schema(implementation = Page.class)))
    })
    @GetMapping("/paged")
    public ResponseEntity<Page<RoleDTO>> getAllPaged(Pageable pageable) {
        return ResponseEntity.ok(service.getAllPaged(pageable));
    }

    @Operation(summary = "Delete role", description = "Deletes a role by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Role deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Role not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @Parameter(description = "ID of the role to delete") @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Patch role", description = "Partially updates a role's fields")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Role patched successfully",
            content = @Content(schema = @Schema(implementation = RoleDTO.class))),
        @ApiResponse(responseCode = "404", description = "Role not found")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<RoleDTO> patch(
        @Parameter(description = "ID of the role to patch") @PathVariable Long id,
        @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(service.patch(id, updates));
    }
}
