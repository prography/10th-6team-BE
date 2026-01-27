package com.prography.zone_2_be.domain.survey.dto;

import com.prography.zone_2_be.domain.survey.entity.Survey;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SurveyFindResponse {

	private Long id;
	private String title;
	private String content;
	private String url;

	@Builder(access = AccessLevel.PRIVATE)
	private SurveyFindResponse(Long id, String title, String content, String url) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.url = url;
	}

	public static SurveyFindResponse from(Survey survey) {
		return SurveyFindResponse.builder()
			.id(survey.getId())
			.title(survey.getTitle())
			.content(survey.getContent())
			.url(survey.getUrl())
			.build();
	}
}
