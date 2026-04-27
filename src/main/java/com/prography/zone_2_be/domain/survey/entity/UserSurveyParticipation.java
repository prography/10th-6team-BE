package com.prography.zone_2_be.domain.survey.entity;

import com.prography.zone_2_be.domain.user.entity.User;
import com.prography.zone_2_be.global.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(uniqueConstraints = {
	@UniqueConstraint(columnNames = {"user_id", "survey_id"})
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSurveyParticipation extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "survey_id", nullable = false)
	private Survey survey;

	@Builder
	private UserSurveyParticipation(User user, Survey survey) {
		this.user = user;
		this.survey = survey;
	}

	public static UserSurveyParticipation of(User user, Survey survey) {
		return UserSurveyParticipation.builder()
			.user(user)
			.survey(survey)
			.build();
	}
}
