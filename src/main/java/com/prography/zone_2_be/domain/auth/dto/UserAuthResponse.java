package com.prography.zone_2_be.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserAuthResponse {
	private final String accessToken;
	private final String refreshToken;
	private final Boolean isNew;

	// 1. 모든 필드를 받는 단일 생성자에만 @Builder를 사용합니다.
	@Builder
	private UserAuthResponse(String accessToken, String refreshToken, Boolean isNew) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.isNew = isNew;
	}

	public static UserAuthResponse of(String accessToken, String refreshToken) {
		return UserAuthResponse.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.isNew(false)
			.build();
	}

	public static UserAuthResponse asNew(String accessToken, String refreshToken) {
		return UserAuthResponse.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.isNew(true)
			.build();
	}
}