package com.prography.zone_2_be.domain.term.agreement.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TermAgreementSaveRequest {
	private Long termId;
	private boolean agreed;
}
