package com.prography.zone_2_be.domain.term.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prography.zone_2_be.domain.term.entity.Term;

@Repository
public interface TermRepository extends JpaRepository<Term, Long> {

	List<Term> getTerms();
}
