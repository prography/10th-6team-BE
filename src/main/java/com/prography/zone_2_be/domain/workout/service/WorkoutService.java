package com.prography.zone_2_be.domain.workout.service;

import org.springframework.stereotype.Service;

import com.prography.zone_2_be.domain.user.repository.UserRepository;
import com.prography.zone_2_be.domain.workout.dto.WorkoutGetFatUsageResponse;
import com.prography.zone_2_be.domain.workout.repository.WorkoutRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkoutService {
	private final WorkoutRepository workoutRepository;
	private final UserRepository userRepository;

	private Double calculateFatUsageInGram(Integer kcalUsage) {
		return ((kcalUsage * 0.65) / 9) - ((kcalUsage * 1.5 * 0.15) / 9);
	}

	public WorkoutGetFatUsageResponse getFatUsage(Integer kcalUsage) {
		return WorkoutGetFatUsageResponse.of(calculateFatUsageInGram(kcalUsage));
	}
}
