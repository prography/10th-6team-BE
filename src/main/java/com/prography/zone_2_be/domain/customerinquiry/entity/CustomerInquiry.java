package com.prography.zone_2_be.domain.customerinquiry.entity;

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
public class CustomerInquiry extends UpdatableEntity {

	@Column(nullable = false, length = 128)
	private String title;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;

	@Column(nullable = false)
	private boolean isPinned;

	@Builder
	public CustomerInquiry(String title, String content, boolean isPinned) {
		this.title = title;
		this.content = content;
		this.isPinned = isPinned;
	}

	public static CustomerInquiry of(String title, String content) {
		return CustomerInquiry.builder()
			.title(title)
			.content(content)
			.isPinned(false)
			.build();
	}

	public void pin() {
		if (!this.isPinned) {
			this.isPinned = true;
		}
	}

	public void unpin() {
		if (this.isPinned) {
			this.isPinned = false;
		}
	}
}
