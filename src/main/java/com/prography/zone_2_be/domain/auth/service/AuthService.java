package com.prography.zone_2_be.domain.auth.service;

import com.prography.zone_2_be.domain.auth.dto.UserAuthRequest;
import com.prography.zone_2_be.domain.auth.dto.UserAuthResponse;
import com.prography.zone_2_be.domain.user.entity.User;
import com.prography.zone_2_be.domain.user.repository.UserRepository;
import com.prography.zone_2_be.global.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    private String createAccessToken(UserAuthRequest dto) {
        return jwtUtil.generateJwtToken(dto.oAuth2Key);
    }

    public UserAuthResponse authorize(UserAuthRequest request) {
        boolean isNew = !userRepository.existsByoAuth2Key(request.oAuth2Key);
        if (isNew){
            userRepository.save(User.forRegister(request.oAuth2Key, request.email));
        }

        String token = this.createAccessToken(request);

        return new UserAuthResponse(token, isNew);
    }
}
