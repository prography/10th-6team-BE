package com.prography.zone_2_be.domain.notice.dto;

import com.prography.zone_2_be.domain.notice.entity.Notice;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeResponseDto {

	private Long id;
	private String title;
	private Long createdAt;

	@Builder
	private NoticeResponseDto(Long id, String title, Long createdAt) {
		this.id = id;
		this.title = title;
		this.createdAt = createdAt;
	}

	public static NoticeResponseDto from(Notice notice) {
		return NoticeResponseDto.builder()
			.id(notice.getId())
			.title(notice.getTitle())
			.createdAt(notice.getCreatedAt())
			.build();
	}
}
