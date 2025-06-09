package com.prography.zone_2_be.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenRefreshResponse {
	private final String accessToken;
	private final String refreshToken;

	@Builder
	private TokenRefreshResponse(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public static TokenRefreshResponse of(String accessToken, String refreshToken) {
		return TokenRefreshResponse.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}
}
