package com.prography.zone_2_be.domain.alarm.entity;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AlarmType {
	WORKOUT_REPORT("workout_report", true),        // 필수 알림
	NEWS_AND_BENEFITS("news_benefits", false);        // 선택적 알림 (마케팅)

	private final String code;
	private final boolean essential;                // 필수 알림 여부

	public static List<AlarmType> getAllTypes() {
		return Arrays.asList(values());
	}
}
