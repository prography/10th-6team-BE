// com.prography.zone_2_be.domain.workout.dto/WorkoutTotalSumsDto.java
package com.prography.zone_2_be.domain.workout.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WorkoutTotalDto {
    private Long totalExecTime;
    private Long totalFatUsage;
    private Long totalKcalUsage;
}