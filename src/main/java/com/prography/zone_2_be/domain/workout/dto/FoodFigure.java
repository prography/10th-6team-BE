package com.prography.zone_2_be.domain.workout.dto;

import lombok.Getter;

public enum FoodFigure {
    // 각 음식에 해당하는 실제 지방 소모량(g) 범위를 min, max 값으로 직접 지정합니다.
    ALMOND_10(0,1, 10),
    PORK_BELLY_STRIP_1(1,11, 20),
    RAMEN_1(2, 21, 30),
    PIZZA_PIECE_2(3,  31, 40),
    CAKE_PIECE_1(4, 41, 50),
    CHICKEN_HALF(5, 51, 60),
    TTEOKBOKKI_AND_FRY(6, 61, 70),
    BURGER_SET_AND_SHAKE(7, 71, 80),
    PIZZA_1(8, 81, 90),
    CHICKEN_1(9, 91, 100),
    PORK_FEET_AND_BEER_500(10, 101, Integer.MAX_VALUE); // 마지막 값은 최대값으로 설정

    @Getter
    private final int value;

    @Getter
    private final int min;

    @Getter
    private final int max;

    FoodFigure(int value, int min, int max) {
        this.value = value;
        this.min = min;
        this.max = max;
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