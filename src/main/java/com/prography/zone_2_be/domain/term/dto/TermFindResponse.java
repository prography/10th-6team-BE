package com.prography.zone_2_be.domain.term.dto;

import com.prography.zone_2_be.domain.term.entity.Term;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TermFindResponse {

	private String content;

	@Builder
	private TermFindResponse(String content) {
		this.content = content;
	}

	public static TermFindResponse from(Term term) {
		return TermFindResponse.builder()
			.content(term.getContent())
			.build();
	}
}
