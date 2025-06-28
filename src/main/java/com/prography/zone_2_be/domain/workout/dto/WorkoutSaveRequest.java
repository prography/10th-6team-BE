package com.prography.zone_2_be.domain.workout.dto;

import com.prography.zone_2_be.domain.workout.entity.Activity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkoutSaveRequest {

	@Positive(message = "execTime은 0보다 커야 합니다.")
	private long execTime;

	@Positive(message = "kcalUsage는 0보다 커야 합니다.")
	private int kcalUsage;

	@Min(value = 0, message = "zone2Rate는 최소 0이어야 합니다.")
	@Max(value = 100, message = "zone2Rate는 최대 100이어야 합니다.")
	private int zone2Rate;

	@NotNull(message = "activity는 필수입니다.")
	private Activity activity;

}
