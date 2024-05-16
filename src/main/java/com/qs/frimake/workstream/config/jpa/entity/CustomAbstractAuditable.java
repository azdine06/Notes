package com.qs.frimake.workstream.config.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public abstract class CustomAbstractAuditable<pk extends Serializable>
        extends CustomAbstractPersistable<pk> {

    @CreatedDate
    private LocalDateTime createdDate;

    @Column(updatable = false, length = 100)
    @CreatedBy
    private String createdBy;
}
