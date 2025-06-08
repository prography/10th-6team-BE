package com.prography.zone_2_be.domain.auth.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class TokenRefreshResponse {
    private final String accessToken;
    private final String refreshToken;
}
