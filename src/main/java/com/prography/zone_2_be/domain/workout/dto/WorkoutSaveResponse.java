package com.prography.zone_2_be.domain.workout.dto;

import com.prography.zone_2_be.domain.workout.entity.Workout;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkoutSaveResponse {

	private String uuid;
	private int activity;

	@Builder(access = AccessLevel.PRIVATE)
	private WorkoutSaveResponse(String uuid, int activity) {
		this.uuid = uuid;
		this.activity = activity;
	}

	public static WorkoutSaveResponse from(Workout workout) {
		return WorkoutSaveResponse.builder()
			.uuid(workout.getUuid())
			.activity(workout.getActivity().getValue())
			.build();
	}
}
