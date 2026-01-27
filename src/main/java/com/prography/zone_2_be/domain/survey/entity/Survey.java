package com.prography.zone_2_be.domain.survey.entity;

import java.time.Instant;

import com.prography.zone_2_be.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Survey extends BaseEntity {

	@Column(nullable = false)
	private String url;

	@Column(nullable = false)
	private String title;

	@Column
	private String content;

	@Column(nullable = false)
	private boolean active;

	@Column
	private Instant deletedAt;

	@Builder
	private Survey(String url, String title, String content, boolean active) {
		this.url = url;
		this.title = title;
		this.content = content;
		this.active = active;
	}

	public static Survey of(String url, String title, String content, boolean active) {
		return Survey.builder()
			.url(url)
			.title(title)
			.content(content)
			.active(active)
			.build();
	}
}
