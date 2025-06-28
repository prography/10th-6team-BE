package com.prography.zone_2_be.domain.auth.service;

import java.time.Instant;
import java.util.Optional;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

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

@Service
@RequiredArgsConstructor
public class AuthService {
	private final UserRepository userRepository;
	private final RefreshTokenHolder refreshTokenHolder;
	private final JwtUtil jwtUtil;

	private final ClientRegistrationRepository clientRegistrationRepository;
	private final OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

	private String createAccessToken(User user) {
		return jwtUtil.generateAccessToken(user.getOauth2Key(), user.getUuid());
	}

	private String createRefreshToken(User user) {
		return jwtUtil.generateRefreshToken(user.getUuid());
	}

	public User createUser(String oauth2Key) {
		User newUser = User.forRegister(oauth2Key);
		return userRepository.save(newUser);
	}

	public UserAuthResponse authorize(UserAuthRequest request) {
		String oauth2Key = getOauth2Key(request.getRegistrationId(), request.getOauth2Key());
		Optional<User> optionalUser = userRepository.findByOauth2Key(oauth2Key);
		boolean isNew = optionalUser.isEmpty(); // Optional이 비어있으면 새로운 사용자

		User user = optionalUser.orElseGet(() -> createUser(oauth2Key));

		String accessToken = this.createAccessToken(user);
		String refreshToken = this.createRefreshToken(user);

		refreshTokenHolder.putRefreshToken(user.getUuid(), refreshToken);

		return UserAuthResponse.of(accessToken, refreshToken, isNew);
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

		return TokenRefreshResponse.of(newAccessToken, newRefreshToken);
	}

	private void checkRefreshToken(final String refreshToken) {
		if (!jwtUtil.validateToken(refreshToken))
			throw new JwtException("Invalid refresh token: " + refreshToken);

		String uuid = jwtUtil.getUuid(refreshToken);
		// refresh token id 조회
		String findToken = refreshTokenHolder.getRefreshToken(uuid);

		if (!findToken.equals(refreshToken)) {
			throw new JwtException("Refresh token does not match: " + refreshToken);
		}

	}

	public String getOauth2Key(String registrationId, String accessToken) {

		ClientRegistration registration =
			clientRegistrationRepository.findByRegistrationId(registrationId);
		if (registration == null) {
			throw new IllegalArgumentException("Unknown OAuth provider: " + registrationId);
		}

		OAuth2AccessToken token = new OAuth2AccessToken(
			OAuth2AccessToken.TokenType.BEARER,
			accessToken,
			Instant.now(),
			Instant.now().plusSeconds(60)  // 임시로 60초 만료
		);

		OAuth2UserRequest userRequest = new OAuth2UserRequest(registration, token);

		OAuth2User oauth2User = delegate.loadUser(userRequest);
		return oauth2User.getName();
	}

}
