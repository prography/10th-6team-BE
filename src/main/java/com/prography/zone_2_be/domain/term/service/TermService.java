package com.prography.zone_2_be.domain.term.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prography.zone_2_be.domain.term.dto.TermFindAllResponse;
import com.prography.zone_2_be.domain.term.dto.TermFindAllVersionResponse;
import com.prography.zone_2_be.domain.term.entity.TermType;
import com.prography.zone_2_be.domain.term.repository.TermRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TermService {

	private final TermRepository termRepository;

	public List<TermFindAllResponse> findAllTerm() {
		return termRepository.findLatestTermsGroupedByType()
			.stream()
			.map(TermFindAllResponse::from)
			.toList();
	}

	public List<TermFindAllVersionResponse> findAllTermVersion(TermType termType) {
		return termRepository.findAllByTermTypeOrderByCreatedAtDesc(termType)
			.stream()
			.map(TermFindAllVersionResponse::from)
			.toList();
	}
}
