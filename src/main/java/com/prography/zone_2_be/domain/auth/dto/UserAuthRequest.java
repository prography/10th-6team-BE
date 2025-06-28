package com.prography.zone_2_be.domain.auth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserAuthRequest {
	@NotNull(message = "OAuth2 키는 필수 입력값입니다.")
	public String oauth2Key;
	@NotNull(message = "registrationId 필수 입력값입니다.")
	public String registrationId;
}

