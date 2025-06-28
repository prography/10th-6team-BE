package com.prography.zone_2_be.domain.workout.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prography.zone_2_be.domain.workout.dto.WorkoutGetFatUsageResponse;
import com.prography.zone_2_be.domain.workout.dto.WorkoutGetHistoryResponse;
import com.prography.zone_2_be.domain.workout.dto.WorkoutGetResultResponse;
import com.prography.zone_2_be.domain.workout.dto.WorkoutGetZone2Response;
import com.prography.zone_2_be.domain.workout.dto.WorkoutSaveRequest;
import com.prography.zone_2_be.domain.workout.service.WorkoutService;
import com.prography.zone_2_be.global.response.ApiResponse;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/workout")
@Validated
public class WorkoutController {
	private final WorkoutService workoutService;

	@GetMapping("/fat_usage")
	public ResponseEntity<ApiResponse<WorkoutGetFatUsageResponse>> calculateFatUsage(
		@RequestParam("kcal_usage") @NotNull(message = "kcal_usage는 필수입니다.") @Positive(message = "kcal_usage는 양수여야 합니다.") Integer kcalUsage) {
		return ApiResponse.success(workoutService.getFatUsage(kcalUsage));
	}

	@GetMapping("/history")
	public ResponseEntity<ApiResponse<WorkoutGetHistoryResponse>> getWorkoutHistory(
		@RequestParam("start_time")
		@NotNull(message = "start_time은 필수입니다.")
		@Min(value = 0, message = "start_time은 0 이상이어야 합니다.")
		Long startTime,

		@RequestParam("end_time")
		@NotNull(message = "end_time은 필수입니다.")
		@Min(value = 0, message = "end_time은 0 이상이어야 합니다.")
		Long endTime,

		@RequestParam("page")
		@Min(value = 0, message = "page는 0 이상이어야 합니다.")
		int page,

		@RequestParam("size")
		@Min(value = 1, message = "size는 1 이상이어야 합니다.")
		int size
	) {
		return ApiResponse.success(workoutService.getWorkoutHistory(startTime, endTime, page, size));
	}

	@GetMapping("/zone2")
	public ResponseEntity<ApiResponse<WorkoutGetZone2Response>> getZone2() {
		return ApiResponse.success(workoutService.getZone2());
	}

	@GetMapping("/result")
	public ResponseEntity<ApiResponse<WorkoutGetResultResponse>> getWorkoutResult(
		@RequestParam(value = "uuid", required = false) @NotBlank(message = "uuid는 필수입니다.") String uuid) {
		WorkoutGetResultResponse result = workoutService.getWorkoutResult(uuid);
		return ApiResponse.success(result);
	}

	@PostMapping
	public ResponseEntity<ApiResponse<Void>> saveWorkout(@RequestBody @Valid WorkoutSaveRequest workoutSaveRequest) {
		workoutService.saveWorkout(workoutSaveRequest);
		return ApiResponse.success();
	}
}
