package com.prography.zone_2_be.domain.user.entity;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Provider {
	KAKAO("kakao"),
	NAVER("naver"),
	GOOGLE("google"),
	APPLE("apple");

	private final String registrationId;

	private static final Map<String, Provider> stringToEnum =
		Arrays.stream(values())
			.collect(Collectors.toMap(
				Provider::getRegistrationId,
				Function.identity()
			));

	public static Provider from(String registrationId) {
		return stringToEnum.get(registrationId);
	}
}
