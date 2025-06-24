package com.prography.zone_2_be.domain.workout.controller;

import com.prography.zone_2_be.domain.workout.dto.WorkoutGetZone2Response;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prography.zone_2_be.domain.workout.dto.WorkoutGetFatUsageResponse;
import com.prography.zone_2_be.domain.workout.dto.WorkoutGetHistoryResponse;
import com.prography.zone_2_be.domain.workout.service.WorkoutService;
import com.prography.zone_2_be.global.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/workout")
@Slf4j
public class WorkoutController {
	private final WorkoutService workoutService;

	@GetMapping("/fat_usage")
	public ResponseEntity<ApiResponse<WorkoutGetFatUsageResponse>> calculateFatUsage(
		@Valid @RequestParam("kcal_usage") Integer kcalUsage) {
		return ApiResponse.success(workoutService.getFatUsage(kcalUsage));
	}

	@GetMapping("/history")
	public ResponseEntity<ApiResponse<WorkoutGetHistoryResponse>> getWorkoutHistory(
		@Valid @RequestParam("start_time") Long startTime,
		@Valid @RequestParam("end_time") Long endTime,
		@Valid @RequestParam("page") int page,
		@Valid @RequestParam("size") int size) {
		return ApiResponse.success(workoutService.getWorkoutHistory(startTime, endTime, page, size));
	}

	@GetMapping("/zone2")
	public ResponseEntity<ApiResponse<WorkoutGetZone2Response>> getZone2(){
		return ApiResponse.success(workoutService.getZone2());
	}
}
