package com.prography.zone_2_be.domain.term.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prography.zone_2_be.domain.term.dto.TermFindAllResponse;
import com.prography.zone_2_be.domain.term.dto.TermFindAllVersionResponse;
import com.prography.zone_2_be.domain.term.dto.TermFindResponse;
import com.prography.zone_2_be.domain.term.entity.TermGroup;
import com.prography.zone_2_be.domain.term.entity.TermType;
import com.prography.zone_2_be.domain.term.service.TermService;
import com.prography.zone_2_be.global.response.ApiResponse;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/term")
@RequiredArgsConstructor
@Validated
public class TermController {

	private final TermService termService;

	@GetMapping
	public ResponseEntity<ApiResponse<List<TermFindAllResponse>>> findAllTerm() {
		List<TermFindAllResponse> terms = termService.findAllTerm();
		return ApiResponse.success(terms);
	}

	@GetMapping("/versions")
	public ResponseEntity<ApiResponse<List<TermFindAllVersionResponse>>> findAllTermVersions(
		@RequestParam(name = "termType", required = false) @NotNull(message = "termType는 필수입니다.") TermType termType
	) {
		List<TermFindAllVersionResponse> response = termService.findAllTermVersion(termType);
		return ApiResponse.success(response);
	}

	@GetMapping("/{termId}")
	public ResponseEntity<ApiResponse<TermFindResponse>> findTerm(
		@PathVariable("termId") @Positive(message = "termId는 1 이상의 값이어야 합니다.") Long termId
	) {
		TermFindResponse response = termService.findTerm(termId);
		return ApiResponse.success(response);
	}

	@GetMapping("/type/{termGroup}")
	public ResponseEntity<ApiResponse<List<TermFindAllResponse>>> findTermByType(
		@PathVariable("termGroup") TermGroup termGroup) {

		List<TermFindAllResponse> response = termService.findTermByType(termGroup);
		return ApiResponse.success(response);
	}
}
