/**
 * Copyright (c) 2025 MyFirst Microservice
 *
 * This class represents the Role entity used for user-role management.
 * It is mapped to the database and extends BaseEntity for audit support.
 *
 * Each Role has a unique identifier and a role name.
 *
 * @author Alagu Nirmal Mahendran
 * @created 2025-04-10
 */
package com.myfirst.microservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * Role entity mapped to the roles table in the database.
 * Extends BaseEntity to inherit auditing fields like created/updated dates and user info.
 */
@Entity
@Data
public class Role extends BaseEntity {

    /**
     * Unique identifier for the role.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the role, e.g., ADMIN, USER, etc.
     */
    private String name;

}
