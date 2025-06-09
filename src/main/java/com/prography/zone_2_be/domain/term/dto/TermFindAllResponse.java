package com.prography.zone_2_be.domain.term.dto;

import com.prography.zone_2_be.domain.term.entity.Term;
import com.prography.zone_2_be.domain.term.entity.TermType;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TermFindAllResponse {

	private String content;
	private TermType termType;
	private Long createdAt;

	@Builder(access = AccessLevel.PRIVATE)
	private TermFindAllResponse(String content, TermType termType, Long createdAt) {
		this.content = content;
		this.termType = termType;
		this.createdAt = createdAt;
	}

	public static TermFindAllResponse from(Term term) {
		return TermFindAllResponse.builder()
			.content(term.getContent())
			.termType(term.getTermType())
			.createdAt(term.getCreatedAt())
			.build();
	}
}
