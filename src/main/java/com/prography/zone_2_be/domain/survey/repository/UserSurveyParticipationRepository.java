package com.prography.zone_2_be.domain.survey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prography.zone_2_be.domain.survey.entity.Survey;
import com.prography.zone_2_be.domain.survey.entity.UserSurveyParticipation;
import com.prography.zone_2_be.domain.user.entity.User;

@Repository
public interface UserSurveyParticipationRepository extends JpaRepository<UserSurveyParticipation, Long> {
	boolean existsByUserAndSurvey(User user, Survey survey);
}
