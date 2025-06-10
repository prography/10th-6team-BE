package com.prography.zone_2_be.domain.term.agreement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prography.zone_2_be.domain.term.agreement.entity.TermAgreement;

@Repository
public interface TermAgreementRepository extends JpaRepository<TermAgreement, Long> {
}
