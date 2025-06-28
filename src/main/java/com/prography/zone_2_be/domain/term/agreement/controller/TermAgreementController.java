package com.prography.zone_2_be.domain.term.agreement.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/term/agreement")
@RequiredArgsConstructor
@Validated
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
		@RequestParam("termIds") @NotEmpty(message = "termIds는 최소 하나 이상 전달되어야 합니다.")
		List<@NotNull(message = "termId는 null일 수 없습니다.")
		@Positive(message = "termId는 양수여야 합니다.") Long> termIds
	) {
		List<TermAgreementFindResponse> response = termAgreementService.getTermAgreementStatus(termIds);
		return ApiResponse.success(response);
	}
}
