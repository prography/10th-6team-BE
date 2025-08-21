package com.prography.zone_2_be.domain.term.entity;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TermGroup {
	SIGN_UP(Arrays.asList(TermType.SERVICE_POLICY, TermType.PERSONAL_INFORMATION_AGREE)),
	MY_PAGE(Arrays.asList(TermType.SERVICE_POLICY, TermType.PRIVACY_POLICY));

	private final List<TermType> terms;
}
