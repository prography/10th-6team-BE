package com.prography.zone_2_be.domain.terms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prography.zone_2_be.domain.terms.dto.TermResponse;
import com.prography.zone_2_be.domain.terms.repository.TermRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TermService {

	private final TermRepository termRepository;

	public List<TermResponse> getTerms() {
		return termRepository.getTerms()
			.stream()
			.map(TermResponse::from)
			.toList();
	}
}
