package com.prography.zone_2_be.domain.workout.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class WorkoutGetFatUsageResponse {
	private final Double result;

	@Builder
	private WorkoutGetFatUsageResponse(Double result) {
		this.result = result;
	}

	public static WorkoutGetFatUsageResponse of(Double result) {
		return WorkoutGetFatUsageResponse.builder()
			.result(result)
			.build();
	}
}
