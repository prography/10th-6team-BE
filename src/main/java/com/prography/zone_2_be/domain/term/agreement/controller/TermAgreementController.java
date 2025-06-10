package com.prography.zone_2_be.domain.term.agreement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prography.zone_2_be.domain.term.agreement.dto.TermAgreementSaveAllRequest;
import com.prography.zone_2_be.domain.term.agreement.service.TermAgreementService;
import com.prography.zone_2_be.global.response.ApiResponse;
import com.prography.zone_2_be.global.utils.JwtUtil;

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
		String uuid = JwtUtil.getUuid();
		termAgreementService.saveAllTermAgreement(uuid, request.getTermAgreementSaveRequests());
		return ApiResponse.success();
	}
}
