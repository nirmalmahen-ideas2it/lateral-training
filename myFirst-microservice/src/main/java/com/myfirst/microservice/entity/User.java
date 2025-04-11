/**
 * Copyright (c) 2025 MyFirst Microservice
 *
 * This class represents the User entity within the application.
 * It includes fields for user identification and a many-to-many
 * relationship with the Role entity.
 *
 * The User entity extends BaseEntity to include auditing fields and versioning.
 *
 * @author Alagu Nirmal Mahendran
 * @created 2025-04-10
 */
package com.myfirst.microservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a system user.
 * Each user can be associated with multiple roles.
 */
@Entity
@Data
@Table(name = "app_user")
public class User extends BaseEntity {

    /**
     * Unique identifier for the user.
     * Generated automatically by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Username of the user. Must be unique within the system.
     */
    private String username;

    /**
     * Email address of the user. Should follow standard email format.
     */
    private String email;

    /**
     * Set of roles assigned to the user.
     * Uses a many-to-many mapping with the Role entity.
     * Eagerly fetched when the user is loaded.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
}
