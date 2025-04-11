/**
 * Copyright (c) 2025 MyFirst Microservice
 *
 * This abstract class provides auditing support for entity classes.
 * It includes fields for created/updated timestamps and user tracking,
 * along with optimistic locking support using a version field.
 *
 * Entities extending this class will inherit these fields automatically
 * through JPA's @MappedSuperclass behavior.
 *
 * @author Alagu Nirmal Mahendran
 * @created 2025-04-10
 */
package com.myfirst.microservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Abstract base class for auditing entity changes.
 * Automatically tracks creation and update timestamps, and user information.
 * Also supports optimistic locking via rowVersion.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class BaseEntity {

    /**
     * Timestamp when the entity was created.
     * This field is set automatically and not updatable.
     */
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created;

    /**
     * Timestamp when the entity was last updated.
     * Updated automatically during update operations.
     */
    @LastModifiedDate
    private LocalDateTime updated;

    /**
     * Username or identifier of the user who created the entity.
     */
    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    /**
     * Username or identifier of the user who last updated the entity.
     */
    @LastModifiedBy
    private String updatedBy;

    /**
     * Version number for optimistic locking. Prevents concurrent updates.
     */
    @Version
    @Column(name = "row_version", nullable = false)
    private long rowVersion;

}
