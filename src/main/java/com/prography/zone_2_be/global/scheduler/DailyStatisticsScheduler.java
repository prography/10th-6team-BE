package com.prography.zone_2_be.global.scheduler;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.prography.zone_2_be.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DailyStatisticsScheduler {

	private static final String WORKOUT_SUCCESS_KEY = "workout:save:success";
	private static final String WORKOUT_FAILURE_KEY = "workout:save:failure";

	private static final String DAILY_STATS_KEY_PREFIX = "daily_stats:";

	private final UserService userService;
	private final RedisTemplate<String, String> redisTemplate;

	/**
	 * 일일 통계 수집 및 저장
	 */
	@Scheduled(cron = "00 58 23 * * *")
	@Transactional
	public void getDailyStatistics() {
		LocalDate targetDate = LocalDate.now();

		// 1. 데이터 집계
		Long workoutSuccessCount = getRedisCount(WORKOUT_SUCCESS_KEY);
		Long workoutFailureCount = getRedisCount(WORKOUT_FAILURE_KEY);
		Long userRegistrationCount = countUserRegistrationsByDate(targetDate);

		// 2. 로그 출력
		log.info("=============================================================================");
		log.info("서비스 활성도 추적 {}: 운동 리포트 저장 성공: {}, 운동 리포트 저장 실패: {}, 회원가입: {}",
			targetDate, workoutSuccessCount, workoutFailureCount, userRegistrationCount);
		log.info("=============================================================================");

		String dailyKey = DAILY_STATS_KEY_PREFIX + targetDate;

		redisTemplate.opsForHash().put(dailyKey, "운동 리포트 저장 성공", String.valueOf(workoutSuccessCount));
		redisTemplate.opsForHash().put(dailyKey, "운동 리포트 저장 실패", String.valueOf(workoutFailureCount));
		redisTemplate.opsForHash().put(dailyKey, "회원가입", String.valueOf(userRegistrationCount));

		// 4. 기존 카운터 초기화
		resetRedisCounters();
	}

	/**
	 * Redis에서 카운트 조회 (값이 없으면 0 반환)
	 */
	private Long getRedisCount(String key) {
		String value = redisTemplate.opsForValue().get(key);
		return value != null ? Long.parseLong(value) : 0L;
	}

	/**
	 * 당일 회원가입 수 조회
	 */
	private Long countUserRegistrationsByDate(LocalDate date) {
		Instant startOfDay = date.atStartOfDay(ZoneId.systemDefault()).toInstant();
		Instant endOfDay = date.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant();

		return userService.countByCreatedAtBetween(startOfDay, endOfDay);
	}

	/**
	 * Redis 카운터 초기화
	 */
	private void resetRedisCounters() {
		redisTemplate.delete(WORKOUT_SUCCESS_KEY);
		redisTemplate.delete(WORKOUT_FAILURE_KEY);
	}
}

