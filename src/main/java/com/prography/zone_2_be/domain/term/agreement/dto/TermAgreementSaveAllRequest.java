package com.prography.zone_2_be.domain.term.agreement.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TermAgreementSaveAllRequest {
	private List<TermAgreementSaveRequest> termAgreementSaveRequests;
}
