package com.prography.zone_2_be.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class UserAuthRequest {
    @NotNull(message = "OAuth2 키는 필수 입력값입니다.")
    public String oauth2Key;

    @NotNull(message = "이메일은 필수 입력값입니다.")
    @Email
    public String email;
}

