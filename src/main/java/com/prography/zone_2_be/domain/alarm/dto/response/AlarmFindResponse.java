package com.prography.zone_2_be.domain.alarm.dto.response;

import com.prography.zone_2_be.domain.alarm.entity.Alarm;
import com.prography.zone_2_be.domain.alarm.entity.AlarmType;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AlarmFindResponse {

	private AlarmType alarmType;

	private boolean enabled;

	@Builder
	private AlarmFindResponse(AlarmType alarmType, boolean enabled) {
		this.alarmType = alarmType;
		this.enabled = enabled;
	}

	public static AlarmFindResponse from(Alarm alarm) {
		return AlarmFindResponse.builder()
			.alarmType(alarm.getAlarmType())
			.enabled(alarm.isEnabled())
			.build();
	}
}
