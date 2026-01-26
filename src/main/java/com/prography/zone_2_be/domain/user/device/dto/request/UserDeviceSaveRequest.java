package com.prography.zone_2_be.domain.user.device.dto.request;

import com.prography.zone_2_be.domain.user.device.entity.OsType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserDeviceSaveRequest {

	private OsType osType;

	private String deviceModel;
}
