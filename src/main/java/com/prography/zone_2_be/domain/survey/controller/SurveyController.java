package com.prography.zone_2_be.domain.survey.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prography.zone_2_be.domain.survey.dto.SurveyEligibilityResponse;
import com.prography.zone_2_be.domain.survey.dto.SurveyFindResponse;
import com.prography.zone_2_be.domain.survey.service.SurveyService;
import com.prography.zone_2_be.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/survey")
public class SurveyController {

	private final SurveyService surveyService;

	@GetMapping("/eligibility")
	public ResponseEntity<ApiResponse<SurveyEligibilityResponse>> checkSurveyEligibility() {
		return ApiResponse.success(surveyService.checkSurveyEligibility());
	}

	@GetMapping
	public ResponseEntity<ApiResponse<SurveyFindResponse>> findActiveSurvey() {
		return ApiResponse.success(surveyService.findActiveSurvey());
	}

	@PostMapping("/{surveyId}/participate")
	public ResponseEntity<ApiResponse<Void>> participate(@PathVariable Long surveyId) {
		surveyService.participateSurvey(surveyId);
		return ApiResponse.success();
	}
}
