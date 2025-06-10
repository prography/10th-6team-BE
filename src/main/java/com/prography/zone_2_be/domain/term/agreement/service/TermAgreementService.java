package com.prography.zone_2_be.domain.term.agreement.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prography.zone_2_be.domain.term.agreement.dto.TermAgreementSaveRequest;
import com.prography.zone_2_be.domain.term.agreement.entity.TermAgreement;
import com.prography.zone_2_be.domain.term.agreement.repository.TermAgreementRepository;
import com.prography.zone_2_be.domain.term.entity.Term;
import com.prography.zone_2_be.domain.term.repository.TermRepository;
import com.prography.zone_2_be.domain.user.entity.User;
import com.prography.zone_2_be.domain.user.repository.UserRepository;
import com.prography.zone_2_be.global.error.ErrorCode;
import com.prography.zone_2_be.global.exception.CustomException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TermAgreementService {

	private final TermAgreementRepository termAgreementRepository;
	private final TermRepository termRepository;
	private final UserRepository userRepository;

	@Transactional
	public void saveAllTermAgreement(String uuid, List<TermAgreementSaveRequest> requests) {
		User user = userRepository.findByUuid(uuid).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

		validateAllAgreed(requests);

		List<Term> terms = findTermsByIds(extractTermIds(requests));

		List<TermAgreement> newAgreements = createNewAgreements(user, terms);

		termAgreementRepository.saveAll(newAgreements);
	}

	private void validateAllAgreed(List<TermAgreementSaveRequest> requests) {
		if (!requests.stream().allMatch(TermAgreementSaveRequest::isAgreed)) {
			throw new CustomException(ErrorCode.REQUIRED_TERMS_NOT_AGREED);
		}
	}

	private List<Long> extractTermIds(List<TermAgreementSaveRequest> requests) {
		return requests.stream()
			.map(TermAgreementSaveRequest::getTermId)
			.distinct()
			.toList();
	}

	private List<Term> findTermsByIds(List<Long> termIds) {
		List<Term> terms = termRepository.findAllById(termIds);
		if (terms.size() != termIds.size()) {
			throw new CustomException(ErrorCode.TERM_NOT_FOUND);
		}
		return terms;
	}

	private List<TermAgreement> createNewAgreements(User user, List<Term> terms) {
		List<TermAgreement> existing = termAgreementRepository.findByUserAndTermIn(user, terms);

		return terms.stream()
			.filter(term -> existing.stream()
				.noneMatch(agreement -> agreement.getTerm().getId().equals(term.getId())))
			.map(term -> TermAgreement.of(term, user))
			.toList();
	}
}
