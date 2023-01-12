package com.backend.todo.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

/**
 * @author Alexey Voronin.
 * @since 29.12.2022.
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditingEntity {

    @Audited
    @CreatedDate
    private Instant createdAt;

    @Audited
    @LastModifiedDate
    private Instant modifiedAt;

    @Audited
    @CreatedBy
    private String createdBy;

    @Audited
    @LastModifiedBy
    private String modifiedBy;
}
