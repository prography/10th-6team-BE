package com.prography.zone_2_be.domain.auth.service;

import com.prography.zone_2_be.domain.auth.dto.TokenRefreshRequest;
import com.prography.zone_2_be.domain.auth.dto.TokenRefreshResponse;
import com.prography.zone_2_be.domain.auth.dto.UserAuthRequest;
import com.prography.zone_2_be.domain.auth.dto.UserAuthResponse;
import com.prography.zone_2_be.domain.user.entity.User;
import com.prography.zone_2_be.domain.user.exception.UserNotFoundException;
import com.prography.zone_2_be.domain.user.repository.UserRepository;
import com.prography.zone_2_be.global.utils.JwtUtil;
import com.prography.zone_2_be.global.utils.RefreshTokenHolder;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RefreshTokenHolder refreshTokenHolder;

    private String createAccessToken(User user) {
        return jwtUtil.generateAccessToken(user.getOauth2Key(), user.getUuid());
    }

    private String createRefreshToken(User user) {
        return jwtUtil.generateRefreshToken(user.getUuid());
    }

    public UserAuthResponse authorize(UserAuthRequest request) {
        Optional<User> optionalUser = userRepository.findByOauth2Key(request.oauth2Key);
        boolean isNew = optionalUser.isEmpty(); // Optional이 비어있으면 새로운 사용자

        User user = optionalUser.orElseGet(() -> {
            User newUser = User.forRegister(request.oauth2Key, request.email);
            return userRepository.save(newUser);
        });

        String accessToken = this.createAccessToken(user);
        String refreshToken = this.createRefreshToken(user);

        refreshTokenHolder.putRefreshToken(user.getUuid(), refreshToken);

        return UserAuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .isNew(isNew)
                .build();
    }

    public TokenRefreshResponse refreshToken(TokenRefreshRequest request) {
        // refresh token 유효성 검증
        String refreshToken = request.getRefreshToken();
        checkRefreshToken(refreshToken);

        String uuid = jwtUtil.getUuid(refreshToken);

        User user = userRepository.findByUuid(uuid)
                .orElseThrow(UserNotFoundException::new);

        String newAccessToken = this.createAccessToken(user);
        String newRefreshToken = this.createRefreshToken(user);

        refreshTokenHolder.removeRefreshToken(uuid);
        refreshTokenHolder.putRefreshToken(uuid, newRefreshToken);

        return TokenRefreshResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    private void checkRefreshToken(final String refreshToken) {
        if(!jwtUtil.validateToken(refreshToken))
            throw new JwtException("Invalid refresh token: " + refreshToken);

        String uuid = jwtUtil.getUuid(refreshToken);
        // refresh token id 조회
        String findToken = refreshTokenHolder.getRefreshToken(uuid);

        if (!findToken.equals(refreshToken)) {
            throw new JwtException("Refresh token does not match: " + refreshToken);
        }

    }

}
