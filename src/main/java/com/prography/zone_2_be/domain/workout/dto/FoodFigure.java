package com.prography.zone_2_be.domain.workout.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


public enum FoodFigure {
    ALMOND_10(0),
    PORK_BELLY_STRIP_1(1),
    RAMEN_1(2),
    PIZZA_PIECE_2(3),
    CAKE_PIECE_1(4),
    CHICKEN_HALF(5),
    TTEOKBOKKI_AND_FRY(6),
    BURGER_SET_AND_SHAKE(7),
    PIZZA_1(8),
    CHICKEN_1(9),
    PORK_FEET_AND_BEER_500(10);

    @Getter
    private final int value;

    @Getter
    private final int min;

    @Getter
    private final int max;


    FoodFigure(int value){
        int interval = 10;
        this.value = value;
        this.min = (value) * interval + 1;
        this.max = (value+1) *  interval;
    }

    public static FoodFigure matchFatUsageAndFoodFigure(int fatUsage) {
        // 모든 FoodFigure 상수를 순회합니다.
        for (FoodFigure food : values()) {
            // fatUsage가 현재 food의 min과 max 사이에 있는지 확인합니다.
            if (fatUsage >= food.getMin() && fatUsage <= food.getMax()) {
                return food; // 일치하는 경우 해당 food를 반환합니다.
            }
        }
        // 모든 상수를 확인했는데도 일치하는 것이 없으면 예외를 발생시킵니다.
        throw new IllegalArgumentException("해당하는 fatUsage에 대한 FoodFigure를 찾을 수 없습니다: " + fatUsage);
    }
}
