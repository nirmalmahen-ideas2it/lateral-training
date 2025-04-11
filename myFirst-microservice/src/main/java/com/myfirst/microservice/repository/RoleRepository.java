/**
 * Copyright (c) 2025 MyFirst Microservice
 *
 * This interface defines the JPA repository for Role entity.
 * It provides standard CRUD operations and pagination support
 * using Spring Data JPA.
 *
 * @author Alagu Nirmal Mahendran
 * @created 2025-04-10
 */
package com.myfirst.microservice.repository;

import com.myfirst.microservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing Role entities from the database.
 *
 * Inherits basic CRUD and pagination methods from JpaRepository.
 * Custom queries (if needed) can be defined here.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
