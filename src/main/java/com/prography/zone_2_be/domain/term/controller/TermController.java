package com.prography.zone_2_be.domain.term.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prography.zone_2_be.domain.term.dto.TermFindAllResponse;
import com.prography.zone_2_be.domain.term.dto.TermFindAllVersionResponse;
import com.prography.zone_2_be.domain.term.dto.TermFindResponse;
import com.prography.zone_2_be.domain.term.entity.TermType;
import com.prography.zone_2_be.domain.term.service.TermService;
import com.prography.zone_2_be.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/term")
@RequiredArgsConstructor
public class TermController {

	private final TermService termService;

	@GetMapping
	public ResponseEntity<ApiResponse<List<TermFindAllResponse>>> findAllTerm() {
		List<TermFindAllResponse> terms = termService.findAllTerm();
		return ApiResponse.success(terms);
	}

	@GetMapping("/versions")
	public ResponseEntity<ApiResponse<List<TermFindAllVersionResponse>>> findAllTermVersions(
		@RequestParam(required = false) TermType termType) {
		List<TermFindAllVersionResponse> response = termService.findAllTermVersion(termType);
		return ApiResponse.success(response);
	}

	@GetMapping("/{termId}")
	public ResponseEntity<ApiResponse<TermFindResponse>> findTerm(
		@PathVariable("termId") Long termId) {
		TermFindResponse response = termService.findTerm(termId);
		return ApiResponse.success(response);
	}
}
