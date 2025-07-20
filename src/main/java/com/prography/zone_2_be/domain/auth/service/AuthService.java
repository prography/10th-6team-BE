package com.prography.zone_2_be.domain.auth.service;

import java.time.Instant;
import java.util.Optional;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prography.zone_2_be.domain.auth.dto.TokenRefreshRequest;
import com.prography.zone_2_be.domain.auth.dto.TokenRefreshResponse;
import com.prography.zone_2_be.domain.auth.dto.UserAuthRequest;
import com.prography.zone_2_be.domain.auth.dto.UserAuthResponse;
import com.prography.zone_2_be.domain.auth.exception.InvalidTokenException;
import com.prography.zone_2_be.domain.auth.exception.OAuth2LoadException;
import com.prography.zone_2_be.domain.auth.repository.AccessTokenRepository;
import com.prography.zone_2_be.domain.auth.repository.RefreshTokenRepository;
import com.prography.zone_2_be.domain.user.entity.User;
import com.prography.zone_2_be.domain.user.exception.UserNotFoundException;
import com.prography.zone_2_be.domain.user.repository.UserRepository;
import com.prography.zone_2_be.global.error.ErrorCode;
import com.prography.zone_2_be.global.utils.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
	private final UserRepository userRepository;
	private final AccessTokenRepository accessTokenRepository;
	private final RefreshTokenRepository refreshTokenRepository;
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

	@Transactional
	public UserAuthResponse authorize(UserAuthRequest request, String oauthToken) {
		// String oauth2Key = getOauth2Key(request.getRegistrationId(), oauthToken);
		String oauth2Key = "key";

		Optional<User> userOpt = userRepository.findByOauth2Key(oauth2Key);

		boolean isNew = userOpt.map(u -> u.getBirth() == null || u.getGender() == null)
			.orElse(true);

		User user = userOpt.orElseGet(() -> createUser(oauth2Key));

		Optional<String> accessTokenOpt = accessTokenRepository.findByUuid(user.getUuid());
		Optional<String> refreshTokenOpt = refreshTokenRepository.findByUuid(user.getUuid());

		if (accessTokenOpt.isPresent() && refreshTokenOpt.isPresent()) {
			return UserAuthResponse.of(accessTokenOpt.get(), refreshTokenOpt.get(), isNew);
		}

		String newAccessToken = createAccessToken(user);
		String newRefreshToken = createRefreshToken(user);

		accessTokenRepository.save(user.getUuid(), newAccessToken);
		refreshTokenRepository.save(user.getUuid(), newRefreshToken);

		return UserAuthResponse.of(newAccessToken, newRefreshToken, isNew);
	}

	@Transactional
	public TokenRefreshResponse refreshToken(TokenRefreshRequest request) {
		String uuid = jwtUtil.getUuid(request.getAccessToken());
		User user = userRepository.findByUuid(uuid)
			.orElseThrow(UserNotFoundException::new);

		// refresh token 유효성 검증
		checkRefreshToken(request.getRefreshToken());

		String newAccessToken = this.createAccessToken(user);
		String newRefreshToken = this.createRefreshToken(user);

		accessTokenRepository.delete(uuid);
		accessTokenRepository.save(uuid, newAccessToken);

		refreshTokenRepository.delete(uuid);
		refreshTokenRepository.save(uuid, newRefreshToken);

		return TokenRefreshResponse.of(newAccessToken, newRefreshToken);
	}

	private void checkRefreshToken(final String refreshToken) {
		if (!jwtUtil.validateToken(refreshToken))
			throw new InvalidTokenException("유효하지 않은 refresh token 입니다.");

		String uuid = jwtUtil.getUuid(refreshToken);
		// refresh token id 조회
		String findToken = refreshTokenRepository.findByUuid(uuid)
			.orElseThrow(() -> new InvalidTokenException(ErrorCode.TOKEN_NOT_FOUND, "요청한 refresh token이 존재하지 않습니다."));

		if (!findToken.equals(refreshToken)) {
			throw new InvalidTokenException("유효하지 않은 refresh token 입니다.");
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

		try {
			OAuth2User oauth2User = delegate.loadUser(userRequest);
			return oauth2User.getName();

		} catch (OAuth2AuthenticationException e) {
			log.error("failed to load oauth2 user: {}", e.getMessage());
			throw new OAuth2LoadException();
		}
	}

	public User getAuthenticatedUser(String requestToken) {
		// 1. JWT 기본 유효성 검사 (만료, 서명 등)
		if (!jwtUtil.validateToken(requestToken)) {
			throw new InvalidTokenException("유효하지 않은 access token 입니다.");
		}

		// 2. Redis에 저장된 토큰과 일치하는지 확인 (화이트리스트 검증)
		String uuid = jwtUtil.getUuid(requestToken);
		String storedToken = accessTokenRepository.findByUuid(uuid)
			.orElseThrow(() -> new InvalidTokenException(ErrorCode.TOKEN_NOT_FOUND, "요청한 access token이 존재하지 않습니다."));

		if (!storedToken.equals(requestToken)) {
			throw new InvalidTokenException("유효하지 않은 access token 입니다.");
		}

		// 3. uuid를 사용하여 User 정보를 조회하여 반환
		return userRepository.findByUuid(uuid).orElseThrow(UserNotFoundException::new);
	}

	@Transactional
	public void logout() {
		User user = JwtUtil.getUser();

		accessTokenRepository.delete(user.getUuid());
		refreshTokenRepository.delete(user.getUuid());
	}

}
