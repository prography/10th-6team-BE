package com.prography.zone_2_be.domain.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AuthResponseDto {
    private final String accessToken;
}