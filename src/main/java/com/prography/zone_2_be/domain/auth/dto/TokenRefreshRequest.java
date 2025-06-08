package com.prography.zone_2_be.domain.auth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class TokenRefreshRequest {
    @NotNull
    private final String refreshToken;
}
