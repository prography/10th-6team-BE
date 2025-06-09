package com.prography.zone_2_be.domain.term.dto;

import com.prography.zone_2_be.domain.term.entity.Term;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TermFindAllVersionResponse {

	private Long id;
	private Long createdAt;

	@Builder
	private TermFindAllVersionResponse(Long id, Long createdAt) {
		this.id = id;
		this.createdAt = createdAt;
	}

	public static TermFindAllVersionResponse from(Term term) {
		return TermFindAllVersionResponse.builder()
			.id(term.getId())
			.createdAt(term.getCreatedAt())
			.build();
	}
}
