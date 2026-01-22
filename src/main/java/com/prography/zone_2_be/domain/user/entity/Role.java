package com.prography.zone_2_be.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
	ADMIN("admin"),
	User("user");

	private final String value;

}
