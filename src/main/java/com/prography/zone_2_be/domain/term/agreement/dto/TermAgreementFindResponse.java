package com.prography.zone_2_be.domain.term.agreement.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TermAgreementFindResponse {

	private Long termId;
	private boolean agreed;

	@Builder
	private TermAgreementFindResponse(Long termId, boolean agreed) {
		this.termId = termId;
		this.agreed = agreed;
	}

	public static TermAgreementFindResponse of(Long termId, boolean agreed) {
		return TermAgreementFindResponse.builder()
			.termId(termId)
			.agreed(agreed)
			.build();
	}
}
