package com.prography.zone_2_be.domain.auth.repository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

/**
 * 현재 유효한 Access Token을 Redis에 저장하고 관리합니다. (Whitelist 방식)
 * 키: "accessToken:{uuid}"
 * 값: Access Token
 */
@Repository
@RequiredArgsConstructor
public class AccessTokenRepository {
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String KEY_PREFIX = "accessToken:";
    private static final Duration ACCESS_TOKEN_VALIDITY = Duration.ofHours(1);

    /**
     * 사용자의 현재 유효한 Access Token을 저장합니다.
     * 동일한 uuid에 대해 새로운 토큰이 저장되면 기존 토큰은 덮어쓰여집니다.
     * @param uuid 사용자의 고유 ID
     * @param accessToken 저장할 Access Token
     */
    public void save(String uuid, String accessToken) {
        String key = KEY_PREFIX + uuid;
        redisTemplate.opsForValue().set(key, accessToken, ACCESS_TOKEN_VALIDITY);
    }

    /**
     * Redis에서 사용자의 현재 유효한 Access Token을 조회합니다.
     * @param uuid 사용자의 고유 ID
     * @return 저장된 Access Token을 담은 Optional 객체
     */
    public Optional<String> findByUuid(String uuid) {
        String key = KEY_PREFIX + uuid;
        String accessToken = (String) redisTemplate.opsForValue().get(key);
        return Optional.ofNullable(accessToken);
    }

    /**
     * Redis에서 사용자의 Access Token을 삭제합니다. (로그아웃 처리)
     * @param uuid 사용자의 고유 ID
     */
    public void delete(String uuid) {
        String key = KEY_PREFIX + uuid;
        redisTemplate.delete(key);
    }
}