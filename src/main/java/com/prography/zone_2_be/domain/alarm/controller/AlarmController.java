package com.prography.zone_2_be.domain.alarm.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prography.zone_2_be.domain.alarm.dto.request.AlarmUpdateRequest;
import com.prography.zone_2_be.domain.alarm.dto.response.AlarmFindResponse;
import com.prography.zone_2_be.domain.alarm.service.AlarmService;
import com.prography.zone_2_be.global.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/alarm")
@Slf4j
public class AlarmController {

	private final AlarmService alarmService;

	/**
	 * 사용자의 알림 전체 조회
	 */
	@GetMapping
	public ResponseEntity<ApiResponse<List<AlarmFindResponse>>> findAllAlarmByUser() {
		List<AlarmFindResponse> response = alarmService.findAllAlarmByUser();
		return ApiResponse.success(response);
	}

	/**
	 * 사용자의 알림 설정 수정
	 */
	@PostMapping
	public ResponseEntity<ApiResponse<Void>> updateAlarm(@Valid @RequestBody AlarmUpdateRequest request) {
		alarmService.updateAlarm(request);
		return ApiResponse.success();
	}
}
