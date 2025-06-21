package com.prography.zone_2_be.domain.workout.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class WorkoutGetFatUsageResponse {
	private final Integer result;

	@Builder
	private WorkoutGetFatUsageResponse(Integer result) {
		this.result = result;
	}

	public static WorkoutGetFatUsageResponse of(Integer result) {
		return WorkoutGetFatUsageResponse.builder()
			.result(result)
			.build();
	}
}
