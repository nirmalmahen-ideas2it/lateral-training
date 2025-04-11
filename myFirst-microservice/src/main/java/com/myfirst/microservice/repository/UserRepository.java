/**
 * Copyright (c) 2025 MyFirst Microservice
 *
 * This interface provides the repository layer for the User entity.
 * It extends JpaRepository to support CRUD operations and pagination.
 *
 * Spring Data JPA will automatically implement this interface at runtime.
 *
 * @author Alagu Nirmal Mahendran
 * @created 2025-04-10
 */
package com.myfirst.microservice.repository;

import com.myfirst.microservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for User entities.
 * Provides methods for database interactions using Spring Data JPA.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
