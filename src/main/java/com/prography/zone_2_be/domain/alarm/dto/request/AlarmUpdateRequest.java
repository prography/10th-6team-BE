package com.prography.zone_2_be.domain.alarm.dto.request;

import com.prography.zone_2_be.domain.alarm.entity.AlarmType;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlarmUpdateRequest {

	@NotNull(message = "alarmType은 필수입니다.")
	private AlarmType alarmType;

	@NotNull(message = "enabled 값은 필수입니다.")
	private Boolean enabled;
}
