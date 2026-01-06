package com.prography.zone_2_be.domain.auth.controller;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import com.prography.zone_2_be.domain.auth.repository.AccessTokenRepository;
import com.prography.zone_2_be.domain.auth.repository.RefreshTokenRepository;
import com.prography.zone_2_be.domain.user.entity.User;
import com.prography.zone_2_be.domain.user.repository.UserRepository;
import com.prography.zone_2_be.global.utils.JwtUtil;

import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("test")
public class AuthControllerTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AccessTokenRepository accessTokenRepository;

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Autowired
	private JwtUtil jwtUtil;

	@Test
	@Transactional
	@Rollback(false)
	void tokenTest() {
		Optional<User> userOpt = userRepository.findByOauth2Key("사용자 oauth2key 입력");

		User user = userOpt.get();

		jwtUtil.generateAccessToken(user.getOauth2Key(), user.getUuid());

		Optional<String> accessTokenOpt = accessTokenRepository.findByUuid(user.getUuid());
		Optional<String> refreshTokenOpt = refreshTokenRepository.findByUuid(user.getUuid());

		String newAccessToken = jwtUtil.generateAccessToken(user.getOauth2Key(), user.getUuid());
		String newRefreshToken = jwtUtil.generateRefreshToken(user.getUuid());

		accessTokenRepository.save(user.getUuid(), newAccessToken);
		refreshTokenRepository.save(user.getUuid(), newRefreshToken);
	}

}
