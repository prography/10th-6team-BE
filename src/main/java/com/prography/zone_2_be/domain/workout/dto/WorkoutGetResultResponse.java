package com.prography.zone_2_be.domain.workout.dto;

import com.prography.zone_2_be.domain.workout.entity.Workout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class WorkoutGetResultResponse {

	private String uuid;
	private Long execTime;
	private Integer zone2Rate;
	private Integer kcalUsage;
	private Integer relativeFatUsage;
	private Integer zone2FatUsage;
	private Integer zone4FatUsage;
	private Integer foodFigure;
	private int activity;
	private Long createdAt;

	public static WorkoutGetResultResponse from(Workout workout, Integer relativeFatUsage, Integer zone2FatUsage,
		Integer zone4FatUsage, FoodFigure foodFigure) {
		return WorkoutGetResultResponse.builder()
			.uuid(workout.getUuid())
			.activity(workout.getActivity().getValue())
			.execTime(workout.getExecTime())
			.zone2Rate(workout.getZone2Rate())
			.kcalUsage(workout.getKcalUsage())
			.relativeFatUsage(relativeFatUsage)
			.zone2FatUsage(zone2FatUsage)
			.zone4FatUsage(zone4FatUsage)
			.foodFigure(foodFigure.getValue())
			.createdAt(workout.getCreatedAt().getEpochSecond())
			.build();
	}
}
