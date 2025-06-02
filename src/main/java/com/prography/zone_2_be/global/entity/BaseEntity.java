package com.prography.zone_2_be.global.entity;

import java.time.Instant;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, updatable = false)
	private Long createdAt;

	@Column(nullable = false)
	private Long updatedAt;

	@PrePersist
	protected void prePersist() {
		long now = Instant.now().toEpochMilli();
		this.createdAt = now;
		this.updatedAt = now;
	}

	@PreUpdate
	protected void preUpdate() {
		this.updatedAt = Instant.now().toEpochMilli();
	}
}
