package com.prography.zone_2_be.domain.alarm.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prography.zone_2_be.domain.alarm.entity.Alarm;
import com.prography.zone_2_be.domain.alarm.entity.AlarmType;
import com.prography.zone_2_be.domain.alarm.repository.AlarmRepository;
import com.prography.zone_2_be.domain.user.entity.User;

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

}
