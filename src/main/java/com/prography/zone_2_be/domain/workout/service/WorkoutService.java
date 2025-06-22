package com.prography.zone_2_be.domain.workout.service;

import com.prography.zone_2_be.domain.user.entity.User;
import com.prography.zone_2_be.domain.user.exception.UserNotFoundException;
import com.prography.zone_2_be.domain.workout.dto.WorkoutGetHistoryResponse;
import com.prography.zone_2_be.domain.workout.dto.WorkoutHistoryDto;
import com.prography.zone_2_be.domain.workout.dto.WorkoutTotalDto;
import com.prography.zone_2_be.domain.workout.entity.Workout;
import com.prography.zone_2_be.global.utils.JwtUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.prography.zone_2_be.domain.user.repository.UserRepository;
import com.prography.zone_2_be.domain.workout.dto.WorkoutGetFatUsageResponse;
import com.prography.zone_2_be.domain.workout.repository.WorkoutRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutService {
	private final WorkoutRepository workoutRepository;
	private final UserRepository userRepository;

	private Integer calculateFatUsageInGram(Integer kcalUsage) {
		double value =  ((kcalUsage * 0.65) / 9) - ((kcalUsage * 1.5 * 0.15) / 9);
		return (int) value;
	}

	public WorkoutGetFatUsageResponse getFatUsage(Integer kcalUsage) {
		return WorkoutGetFatUsageResponse.of(calculateFatUsageInGram(kcalUsage));
	}

	public WorkoutGetHistoryResponse getWorkoutHistory(Long startTime, Long endTime, int page, int size) {
		User user = JwtUtil.getUser();

		// 1. 페이지네이션된 개별 운동 기록 조회
		Pageable pageable = PageRequest.of(page, size);
		List<Workout> workouts = workoutRepository.findByUserAndCreatedAtGreaterThanEqualAndCreatedAtLessThanOrderByCreatedAtDesc(
				user, startTime, endTime, pageable);

		// 조회된 Workout 엔티티 리스트를 WorkoutHistoryDto 리스트로 변환
		List<WorkoutHistoryDto> histories = workouts.stream()
				.map(WorkoutHistoryDto::from) // WorkoutHistoryDto의 팩토리 메서드 사용
				.collect(Collectors.toList());

		// 2. 전체 합계 조회 (DB에서 직접 SUM)
		Optional<WorkoutTotalDto> sumsOptional = workoutRepository.findTotalSumsByUserIdAndCreatedAtRange(
				user, startTime, endTime);

		// 결과가 없을 경우를 대비하여 기본값 설정 (모두 0L)
		WorkoutTotalDto total = sumsOptional.orElse(new WorkoutTotalDto(0L, 0L, 0L));

		// 3. 두 결과를 최종 WorkoutGetHistoryResponse DTO에 담아 반환
		return new WorkoutGetHistoryResponse(
				total.getTotalExecTime(),
				total.getTotalFatUsage(),
				total.getTotalKcalUsage(),
				histories
		);
	}
}
