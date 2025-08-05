package com.prography.zone_2_be.domain.auth.repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

import com.prography.zone_2_be.global.utils.JwtUtil;

/**
 * 현재 유효한 Refresh Token을 Redis에 저장하고 관리합니다. (Whitelist 방식)
 * 키: "refreshToken:{uuid}"
 * 값: Refresh Token
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenRepository {
	private final JwtUtil jwtUtil;
	private final RedisTemplate<String, Object> redisTemplate;
	private static final String KEY_PREFIX = "refreshToken:";


	/**
	 * 사용자의 현재 유효한 Refresh Token을 저장합니다.
	 * 동일한 uuid에 대해 새로운 토큰이 저장되면 기존 토큰은 덮어쓰여집니다.
	 * @param uuid 사용자의 고유 ID
	 * @param refreshToken 저장할 Refresh Token
	 */
	public void save(String uuid, String refreshToken) {
		String key = KEY_PREFIX + uuid;
		log.info("refresh_token: save key {}", key);
		Duration expiration = Duration.ofMillis(jwtUtil.getRefreshTokenExpiration());
		redisTemplate.opsForValue().set(key, refreshToken, expiration);
	}

	/**
	 * Redis에서 사용자의 현재 유효한 Refresh Token을 조회합니다.
	 * @param uuid 사용자의 고유 ID
	 * @return 저장된 Refresh Token을 담은 Optional 객체
	 */
	public Optional<String> findByUuid(String uuid) {
		String key = KEY_PREFIX + uuid;
		log.info("refresh_token: find key: {}", key);
		String refreshToken = (String) redisTemplate.opsForValue().get(key);
		return Optional.ofNullable(refreshToken);
	}

	/**
	 * Redis에서 사용자의 Refresh Token을 삭제합니다. (로그아웃 처리)
	 * @param uuid 사용자의 고유 ID
	 */
	public void delete(String uuid) {
		String key = KEY_PREFIX + uuid;
		log.info("refresh_token: delete key: {}", key);
		redisTemplate.delete(key);
	}
}