package com.prography.zone_2_be.domain.workout.dto;


import com.prography.zone_2_be.domain.workout.entity.Workout;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@AllArgsConstructor
@Getter
public class WorkoutGetResultResponse {
    private Long execTime;
    private Integer zone2Rate;
    private Integer kcalUsage;
    private Integer relativeFatUsage;
    private Integer foodFigure;

    public static WorkoutGetResultResponse from(Workout workout, Integer relativeFatUsage, FoodFigure foodFigure) {
        return WorkoutGetResultResponse.builder()
                .execTime(workout.getExecTime())
                .zone2Rate(workout.getZone2Rate())
                .kcalUsage(workout.getKcalUsage())
                .relativeFatUsage(relativeFatUsage)
                .foodFigure(foodFigure.getValue())
                .build();
    }
}
