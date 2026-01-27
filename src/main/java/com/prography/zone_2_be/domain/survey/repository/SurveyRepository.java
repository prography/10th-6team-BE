package com.prography.zone_2_be.domain.survey.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prography.zone_2_be.domain.survey.entity.Survey;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {
	Optional<Survey> findByActiveTrue();

	Optional<Survey> findByIdAndActiveTrue(Long id);
}
