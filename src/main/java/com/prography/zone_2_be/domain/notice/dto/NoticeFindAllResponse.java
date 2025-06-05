package com.prography.zone_2_be.domain.notice.dto;

import com.prography.zone_2_be.domain.notice.entity.Notice;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeFindAllResponse {

	private Long id;
	private String title;
	private Long createdAt;

	@Builder
	private NoticeFindAllResponse(Long id, String title, Long createdAt) {
		this.id = id;
		this.title = title;
		this.createdAt = createdAt;
	}

	public static NoticeFindAllResponse from(Notice notice) {
		return NoticeFindAllResponse.builder()
			.id(notice.getId())
			.title(notice.getTitle())
			.createdAt(notice.getCreatedAt())
			.build();
	}
}
