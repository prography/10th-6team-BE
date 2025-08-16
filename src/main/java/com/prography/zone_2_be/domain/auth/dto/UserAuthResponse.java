package com.prography.zone_2_be.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserAuthResponse {
	private final String accessToken;
	private final String refreshToken;

	@Builder
	private UserAuthResponse(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public static UserAuthResponse of(String accessToken, String refreshToken) {
		return UserAuthResponse.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}
}