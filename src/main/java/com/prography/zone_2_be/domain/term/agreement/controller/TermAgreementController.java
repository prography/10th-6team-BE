package com.prography.zone_2_be.domain.term.agreement.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prography.zone_2_be.domain.term.agreement.dto.TermAgreementFindResponse;
import com.prography.zone_2_be.domain.term.agreement.dto.TermAgreementSaveAllRequest;
import com.prography.zone_2_be.domain.term.agreement.service.TermAgreementService;
import com.prography.zone_2_be.global.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/term/agreement")
@RequiredArgsConstructor
public class TermAgreementController {

	private final TermAgreementService termAgreementService;

	@PostMapping("/agree-all")
	public ResponseEntity<ApiResponse<Void>> saveAllTermAgreement(
		@RequestBody @Valid TermAgreementSaveAllRequest request) {
		termAgreementService.saveAllTermAgreement(request.getTermAgreementSaveRequests());
		return ApiResponse.success();
	}

	@GetMapping("/status")
	public ResponseEntity<ApiResponse<List<TermAgreementFindResponse>>> getTermAgreementStatus(
		@RequestParam List<Long> termIds
	) {
		List<TermAgreementFindResponse> response = termAgreementService.getTermAgreementStatus(termIds);
		return ApiResponse.success(response);
	}
}
