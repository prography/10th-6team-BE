package com.prography.zone_2_be.global.entity;

import com.prography.zone_2_be.global.converter.InstantToEpochSecondConverter;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(nullable = false, updatable = false, columnDefinition = "BIGINT")
    @Convert(converter = InstantToEpochSecondConverter.class)
    private Long createdAt;
}
