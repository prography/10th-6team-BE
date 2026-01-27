package com.prography.zone_2_be.global.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.prography.zone_2_be.global.error.ErrorCode;
import com.prography.zone_2_be.global.exception.CustomException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class WorkoutCountingAspect {

	private static final String WORKOUT_SUCCESS_KEY = "workout:save:success";
	private static final String WORKOUT_FAILURE_KEY = "workout:save:failure";

	private final RedisTemplate<String, String> redisTemplate;

	/**
	 * saveWorkout 메서드가 정상 반환되면 성공 카운트 증가
	 */
	@AfterReturning("execution(* com.prography.zone_2_be.domain.workout.service.WorkoutService.saveWorkout(..))")
	public void countSuccess() {
		redisTemplate.opsForValue().increment(WORKOUT_SUCCESS_KEY);
	}

	/**
	 * saveWorkout 메서드에서 CustomException 발생 시 실패 카운트 증가
	 */
	@AfterThrowing(
		pointcut = "execution(* com.prography.zone_2_be.domain.workout.service.WorkoutService.saveWorkout(..))",
		throwing = "ex"
	)
	public void countFailure(CustomException ex) {
		if (ex.getErrorCode() == ErrorCode.WORKOUT_REQUIREMENTS_NOT_MET) {
			redisTemplate.opsForValue().increment(WORKOUT_FAILURE_KEY);
		}
	}
}


