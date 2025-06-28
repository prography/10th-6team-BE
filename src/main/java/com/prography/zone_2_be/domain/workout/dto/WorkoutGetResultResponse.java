package com.prography.zone_2_be.domain.workout.dto;


import com.prography.zone_2_be.domain.workout.entity.Workout;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
public class WorkoutGetResultResponse {
    private Long execTime;
    private Integer zone2Rate;
    private Integer kcalUsage;
    private Integer fatUsage;
    private FoodFigure foodFigure;

    public static WorkoutGetResultResponse from(Workout workout, FoodFigure foodFigure) {
        return WorkoutGetResultResponse.builder()
                .execTime(workout.getExecTime())
                .zone2Rate(workout.getZone2Rate())
                .kcalUsage(workout.getKcalUsage())
                .fatUsage(workout.getFatUsage())
                .foodFigure(foodFigure)
                .build();
    }
}
