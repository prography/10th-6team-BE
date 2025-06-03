package com.prography.zone_2_be.domain.terms.dto;

import com.prography.zone_2_be.domain.terms.entity.Term;
import com.prography.zone_2_be.domain.terms.entity.TermType;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TermResponse {

	private String content;
	private TermType termType;
	private Long createdAt;

	@Builder(access = AccessLevel.PRIVATE)
	private TermResponse(String content, TermType termType, Long createdAt) {
		this.content = content;
		this.termType = termType;
		this.createdAt = createdAt;
	}

	public static TermResponse from(Term term) {
		return TermResponse.builder()
			.content(term.getContent())
			.termType(term.getTermType())
			.createdAt(term.getCreatedAt())
			.build();
	}
}
