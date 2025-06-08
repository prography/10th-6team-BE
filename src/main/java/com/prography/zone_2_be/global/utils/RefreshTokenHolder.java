package com.prography.zone_2_be.global.utils;

import io.jsonwebtoken.JwtException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 배포 시에는 단일 인스턴스 서버로 사용할 것이기에 본 클래스의 hashmap을 통해 refresh token을 관리한다.
 * 이후 서버를 확장할 경우에는 Redis와 같은 외부 저장소를 사용하여 refresh token을 관리해야 한다.
 */
@Component
public class RefreshTokenHolder {
    protected final Map<String, String> refreshTokens = new HashMap<>();

    public String getRefreshToken(final String uuid) {
        return Optional.ofNullable(refreshTokens.get(uuid))
                .orElseThrow(() -> new JwtException("Refresh token not found: " + uuid));
    }

    public void putRefreshToken(String uuid, final String refreshToken) {
        refreshTokens.put(uuid, refreshToken);
    }


    public void removeRefreshToken(String uuid) {
        refreshTokens.remove(uuid);
    }

}
