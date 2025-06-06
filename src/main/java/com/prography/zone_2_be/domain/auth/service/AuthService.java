package com.prography.zone_2_be.domain.auth.service;

import com.prography.zone_2_be.domain.auth.dto.AuthRequestDto;
import com.prography.zone_2_be.domain.auth.dto.AuthResponseDto;
import com.prography.zone_2_be.domain.user.entity.User;
import com.prography.zone_2_be.domain.user.repository.UserRepository;
import com.prography.zone_2_be.global.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    private String createAccessToken(AuthRequestDto dto) {
        return jwtUtil.generateJwtToken(dto.oAuth2Key);
    }

    public AuthResponseDto authorize(AuthRequestDto request) {
        boolean isNew = !userRepository.existsByoAuth2Key(request.oAuth2Key);
        if (isNew){
            userRepository.save(User.forRegister(request.oAuth2Key, request.email));
        }

        String token = this.createAccessToken(request);

        return new AuthResponseDto(token, isNew);
    }
}
