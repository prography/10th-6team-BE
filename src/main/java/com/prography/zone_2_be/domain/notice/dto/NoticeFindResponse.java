package com.prography.zone_2_be.domain.notice.dto;

import com.prography.zone_2_be.domain.notice.entity.Notice;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NoticeFindResponse {

	private String title;
	private String content;
	private Long createdAt;

	@Builder
	private NoticeFindResponse(String title, String content, Long createdAt) {
		this.title = title;
		this.content = content;
		this.createdAt = createdAt;
	}

	public static NoticeFindResponse from(Notice notice) {
		return NoticeFindResponse.builder()
			.title(notice.getTitle())
			.content(notice.getContent())
			.createdAt(notice.getCreatedAt())
			.build();
	}
}
