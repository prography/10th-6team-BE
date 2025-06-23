// com.prography.zone_2_be.domain.workout.dto/WorkoutGetHistoryResponse.java
package com.prography.zone_2_be.domain.workout.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutGetHistoryResponse {
	private Long totalExecTime;
	private Long totalKcalUsage;
	private Long totalZone2FatUsage;

	// 개별 운동 기록 DTO 리스트
	private List<WorkoutHistoryDto> histories;
}