package com.prography.zone_2_be.domain.term.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.prography.zone_2_be.domain.term.entity.Term;
import com.prography.zone_2_be.domain.term.entity.TermType;

@Repository
public interface TermRepository extends JpaRepository<Term, Long> {

	@Query(value = """
		SELECT t.*
		FROM term t
		JOIN (
		    SELECT term_type, MAX(created_at) AS max_created
		    FROM term
		    GROUP BY term_type
		) m ON t.term_type = m.term_type AND t.created_at = m.max_created
		""", nativeQuery = true)
	List<Term> findLatestTermsGroupedByType();

	List<Term> findAllByTermTypeOrderByCreatedAtDesc(TermType type);
}
