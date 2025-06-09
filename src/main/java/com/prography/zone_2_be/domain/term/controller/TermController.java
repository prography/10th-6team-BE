package com.prography.zone_2_be.domain.term.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prography.zone_2_be.domain.term.dto.TermFindAllResponse;
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
}
