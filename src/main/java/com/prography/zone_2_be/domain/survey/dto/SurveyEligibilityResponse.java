package com.prography.zone_2_be.domain.survey.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SurveyEligibilityResponse {

	@JsonProperty("isEligible")
	private boolean eligible;

	@Builder
	private SurveyEligibilityResponse(boolean eligible) {
		this.eligible = eligible;
	}

	public static SurveyEligibilityResponse of(boolean eligible) {
		return SurveyEligibilityResponse.builder()
			.eligible(eligible)
			.build();
	}
}
