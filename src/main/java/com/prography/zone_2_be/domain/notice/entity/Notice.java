package com.prography.zone_2_be.domain.notice.entity;

import com.prography.zone_2_be.global.entity.UpdatableEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
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

	@Builder
	private Notice(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public static Notice of(String title, String content) {
		return Notice.builder()
			.title(title)
			.content(content)
			.build();
	}
}
