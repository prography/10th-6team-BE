// com.prography.zone_2_be.domain.workout.dto/WorkoutHistoryDto.java (예시)
package com.prography.zone_2_be.domain.workout.dto;

import com.prography.zone_2_be.domain.workout.entity.Activity;
import com.prography.zone_2_be.domain.workout.entity.Workout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class WorkoutHistoryDto {
	private Long execTime;
	private Integer kcalUsage;
	private Integer fatUsage;
	private Activity activity;

	public static WorkoutHistoryDto from(Workout workout) {
		return WorkoutHistoryDto.builder()
			.execTime(workout.getExecTime())
			.kcalUsage(workout.getKcalUsage())
			.fatUsage(workout.getFatUsage())
			.activity(workout.getActivity())
			.build();

	}
}