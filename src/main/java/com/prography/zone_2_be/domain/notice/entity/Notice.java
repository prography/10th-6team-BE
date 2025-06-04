package com.prography.zone_2_be.domain.notice.entity;

import com.prography.zone_2_be.global.entity.UpdatableEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends UpdatableEntity {

	@Column(nullable = false, length = 128)
	private String title;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;
}
