package com.prography.zone_2_be.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserAuthResponse {
	private final String accessToken;
	private final String refreshToken;
	private final boolean isNew;

	@Builder
	private UserAuthResponse(String accessToken, String refreshToken, boolean isNew) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.isNew = isNew;
	}

	public static UserAuthResponse of(String accessToken, String refreshToken, boolean isNew) {
		return UserAuthResponse.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.isNew(isNew)
			.build();
	}
}