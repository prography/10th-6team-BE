package com.prography.zone_2_be.domain.term.agreement.dto;

import java.util.List;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TermAgreementSaveAllRequest {

	@Valid
	private List<TermAgreementSaveRequest> termAgreementSaveRequests;
}
