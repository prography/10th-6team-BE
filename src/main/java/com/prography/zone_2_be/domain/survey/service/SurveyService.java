package com.prography.zone_2_be.domain.survey.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prography.zone_2_be.domain.survey.dto.SurveyEligibilityResponse;
import com.prography.zone_2_be.domain.survey.dto.SurveyFindResponse;
import com.prography.zone_2_be.domain.survey.entity.Survey;
import com.prography.zone_2_be.domain.survey.entity.UserSurveyParticipation;
import com.prography.zone_2_be.domain.survey.repository.SurveyRepository;
import com.prography.zone_2_be.domain.survey.repository.UserSurveyParticipationRepository;
import com.prography.zone_2_be.domain.user.entity.User;
import com.prography.zone_2_be.domain.workout.service.WorkoutService;
import com.prography.zone_2_be.global.error.ErrorCode;
import com.prography.zone_2_be.global.exception.CustomException;
import com.prography.zone_2_be.global.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SurveyService {

	private final SurveyRepository surveyRepository;
	private final UserSurveyParticipationRepository userSurveyParticipationRepository;
	private final WorkoutService workoutService;

	public SurveyEligibilityResponse checkSurveyEligibility() {
		User user = JwtUtil.getUser();

		Survey activeSurvey = surveyRepository.findByActiveTrue().orElse(null);
		if (activeSurvey == null) {
			return SurveyEligibilityResponse.of(false);
		}

		if (userSurveyParticipationRepository.existsByUserAndSurvey(user, activeSurvey)) {
			return SurveyEligibilityResponse.of(false);
		}

		return SurveyEligibilityResponse.of(isEligibleWorkoutCount(user));
	}

	public SurveyFindResponse findActiveSurvey() {
		Survey activeSurvey = surveyRepository.findByActiveTrue()
			.orElseThrow(() -> new CustomException(ErrorCode.SURVEY_NOT_FOUND));
		return SurveyFindResponse.from(activeSurvey);
	}

	@Transactional
	public void participateSurvey(Long surveyId) {
		User user = JwtUtil.getUser();

		Survey survey = surveyRepository.findByIdAndActiveTrue(surveyId)
			.orElseThrow(() -> new CustomException(ErrorCode.SURVEY_NOT_FOUND));

		if (userSurveyParticipationRepository.existsByUserAndSurvey(user, survey)) {
			throw new CustomException(ErrorCode.SURVEY_ALREADY_RESPONDED);
		}

		if (!isEligibleWorkoutCount(user)) {
			throw new CustomException(ErrorCode.SURVEY_NOT_ELIGIBLE);
		}

		userSurveyParticipationRepository.save(UserSurveyParticipation.of(user, survey));
	}

	private boolean isEligibleWorkoutCount(User user) {
		long workoutCount = workoutService.countUserWorkouts(user);
		return workoutCount == 2 || workoutCount == 6 || workoutCount == 10;
	}
}
