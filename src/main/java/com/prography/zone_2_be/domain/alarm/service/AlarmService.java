package com.prography.zone_2_be.domain.alarm.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prography.zone_2_be.domain.alarm.dto.request.AlarmUpdateRequest;
import com.prography.zone_2_be.domain.alarm.dto.response.AlarmFindResponse;
import com.prography.zone_2_be.domain.alarm.entity.Alarm;
import com.prography.zone_2_be.domain.alarm.entity.AlarmType;
import com.prography.zone_2_be.domain.alarm.repository.AlarmRepository;
import com.prography.zone_2_be.domain.user.entity.User;
import com.prography.zone_2_be.global.error.ErrorCode;
import com.prography.zone_2_be.global.exception.CustomException;
import com.prography.zone_2_be.global.utils.JwtUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlarmService {

	private final AlarmRepository alarmRepository;

	/**
	 * 회원가입 시 알림 설정 초기화
	 */
	@Transactional
	public void initializeAlarmByUser(User user) {

		List<Alarm> alarms = AlarmType.getAllTypes().stream()
			.map(type -> Alarm.of(user, type))
			.toList();

		alarmRepository.saveAll(alarms);
	}

	/**
	 * 사용자의 모든 알림 설정 조회
	 */
	@Transactional(readOnly = true)
	public List<AlarmFindResponse> findAllAlarmByUser() {
		User user = JwtUtil.getUser();

		return alarmRepository.findAllByUser(user).stream()
			.map(AlarmFindResponse::from)
			.toList();
	}

	/**
	 * 사용자의 알림 설정 수정
	 */
	@Transactional
	public void updateAlarm(@Valid AlarmUpdateRequest request) {

		User user = JwtUtil.getUser();

		Alarm alarm = alarmRepository
			.findByUserAndAlarmType(user, request.getAlarmType())
			.orElseThrow(() -> new CustomException(ErrorCode.ALARM_NOT_FOUND));

		alarm.updateEnabled(request.getEnabled());
	}
}
