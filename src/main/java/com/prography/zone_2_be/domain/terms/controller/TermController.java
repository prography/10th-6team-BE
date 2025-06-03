package com.prography.zone_2_be.domain.terms.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prography.zone_2_be.domain.terms.dto.TermResponse;
import com.prography.zone_2_be.domain.terms.service.TermService;
import com.prography.zone_2_be.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/term")
@RequiredArgsConstructor
public class TermController {

	private final TermService termService;

	@GetMapping
	public ResponseEntity<ApiResponse<List<TermResponse>>> TermController() {
		List<TermResponse> terms = termService.getTerms();
		return ApiResponse.success(terms);
	}
}
