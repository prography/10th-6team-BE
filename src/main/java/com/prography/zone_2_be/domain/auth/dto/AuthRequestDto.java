package com.prography.zone_2_be.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class AuthRequestDto {
    @NotNull
    public String oAuth2Key;

    @NotNull
    @Email
    public String email;
}

