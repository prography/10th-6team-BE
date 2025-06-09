package com.prography.zone_2_be.domain.term.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prography.zone_2_be.domain.term.dto.TermFindAllResponse;
import com.prography.zone_2_be.domain.term.repository.TermRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TermService {

	private final TermRepository termRepository;

	public List<TermFindAllResponse> getTerms() {
		return termRepository.getTerms()
			.stream()
			.map(TermFindAllResponse::from)
			.toList();
	}
}
