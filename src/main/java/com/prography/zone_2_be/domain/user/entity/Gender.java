package com.prography.zone_2_be.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {
	MALE(0),
	FEMALE(1);

	private final int value;

}
