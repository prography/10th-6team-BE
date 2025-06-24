package com.prography.zone_2_be.domain.workout.service;

import com.prography.zone_2_be.domain.user.entity.User;
import com.prography.zone_2_be.domain.workout.dto.*;
import com.prography.zone_2_be.domain.workout.entity.Workout;
import com.prography.zone_2_be.domain.workout.repository.WorkoutRepository;
import com.prography.zone_2_be.global.utils.JwtUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.prography.zone_2_be.domain.user.repository.UserRepository;
import com.prography.zone_2_be.domain.workout.dto.WorkoutGetZone2Response;

import com.prography.zone_2_be.domain.workout.dto.WorkoutGetFatUsageResponse;

import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
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

		// Long (초 단위)을 Instant 객체로 변환
		Instant startInstant = Instant.ofEpochSecond(startTime);
		Instant endInstant = Instant.ofEpochSecond(endTime);


		// 1. 페이지네이션된 개별 운동 기록 조회
		Pageable pageable = PageRequest.of(page, size);
		List<Workout> workouts = workoutRepository.findWorkoutsByUserIdAndCreatedAtRange(
				user, startInstant, endInstant, pageable);

		// 조회된 Workout 엔티티 리스트를 WorkoutHistoryDto 리스트로 변환
		List<WorkoutHistoryDto> histories = workouts.stream()
				.map(WorkoutHistoryDto::from) // WorkoutHistoryDto의 팩토리 메서드 사용
				.collect(Collectors.toList());

		// 2. 전체 합계 조회 (DB에서 직접 SUM) - 반환 타입이 IWorkoutTotalDto로 변경
		Optional<IWorkoutTotalDto> sumsOptional = workoutRepository.findTotalSumsByUserIdAndCreatedAtRange(
				user, startInstant, endInstant);

		IWorkoutTotalDto total = sumsOptional.orElseGet(() -> new IWorkoutTotalDto() {
			@Override
			public Long getExecTimeSum() { return 0L; }
			@Override
			public Long getFatUsageSum() { return 0L; }
			@Override
			public Long getKcalUsageSum() { return 0L; }
		});
		// 3. 두 결과를 최종 WorkoutGetHistoryResponse DTO에 담아 반환
		return new WorkoutGetHistoryResponse(
				total.getExecTimeSum(),
				total.getFatUsageSum(),
				total.getKcalUsageSum(),
				histories
		);

	}

	public WorkoutGetZone2Response getZone2() {
		User user = JwtUtil.getUser();

		LocalDate currentDate = LocalDate.now(); // 현재 날짜 가져오기

		// 태어난 날짜가 현재 날짜보다 미래일 경우
		if (user.getBirth().isAfter(currentDate)) {
			throw new IllegalArgumentException("생년월일이 현재 날짜보다 미래일 수 없습니다.");
		}

		// Period.between()을 사용하여 기간 계산
		Period period = Period.between(user.getBirth(), currentDate);

		// 년도만 가져오면 그것이 바로 만나이
		int age = period.getYears();
		return new WorkoutGetZone2Response((int)((220-age)*0.6), (int)((220-age)*0.7));
	}
}
