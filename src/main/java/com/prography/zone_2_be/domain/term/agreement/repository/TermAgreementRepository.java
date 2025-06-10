package com.prography.zone_2_be.domain.term.agreement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prography.zone_2_be.domain.term.agreement.entity.TermAgreement;
import com.prography.zone_2_be.domain.term.entity.Term;
import com.prography.zone_2_be.domain.user.entity.User;

@Repository
public interface TermAgreementRepository extends JpaRepository<TermAgreement, Long> {
	
	List<TermAgreement> findByUserAndTermIn(User user, List<Term> terms);
}
