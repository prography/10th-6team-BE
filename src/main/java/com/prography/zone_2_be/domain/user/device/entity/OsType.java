package com.prography.zone_2_be.domain.user.device.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OsType {
	ANDROID("android"),
	IOS("ios");

	private final String value;
}
