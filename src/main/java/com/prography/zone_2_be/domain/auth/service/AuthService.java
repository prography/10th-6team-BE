package com.prography.zone_2_be.domain.auth.service;

import com.prography.zone_2_be.domain.auth.dto.AuthRequestDto;
import com.prography.zone_2_be.global.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;

    @Transactional
    public String createAccessToken(AuthRequestDto dto) {
        return jwtUtil.generateJwtToken(dto.oAuth2Key);
    }
}
