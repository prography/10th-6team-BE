package com.prography.zone_2_be.domain.workout.exception;

import com.prography.zone_2_be.global.error.ErrorCode;
import com.prography.zone_2_be.global.exception.CustomException;

public class WorkoutNotFoundException extends CustomException {
    public WorkoutNotFoundException() {
        super(ErrorCode.WORKOUT_NOT_FOUND);
    }
}