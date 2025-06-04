package com.prography.zone_2_be.global.entity;

import org.springframework.data.annotation.LastModifiedDate;

import com.prography.zone_2_be.global.converter.InstantToEpochSecondConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class UpdatableEntity extends BaseEntity {

	@LastModifiedDate
	@Column(nullable = false, columnDefinition = "BIGINT")
	@Convert(converter = InstantToEpochSecondConverter.class)
	private Long updatedAt;
}
