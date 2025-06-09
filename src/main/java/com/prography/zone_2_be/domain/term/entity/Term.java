package com.prography.zone_2_be.domain.term.entity;

import com.prography.zone_2_be.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Term extends BaseEntity {

	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, columnDefinition = "VARCHAR(128)")
	private TermType termType;
}
