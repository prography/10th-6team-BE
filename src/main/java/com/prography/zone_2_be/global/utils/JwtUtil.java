package com.prography.zone_2_be.global.utils;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.prography.zone_2_be.domain.user.entity.User;
import com.prography.zone_2_be.global.error.ErrorCode;
import com.prography.zone_2_be.global.exception.CustomException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtil {
	private final SecretKey key;
	@Getter
	private final long accessTokenExpiration;
	@Getter
	private final long refreshTokenExpiration;

	public JwtUtil(
		@Value("${jwt.secret}") String secretKey,
		@Value("${jwt.expirationMs.access-token}") long accessTokenExpiration,
		@Value("${jwt.expirationMs.refresh-token}") long refreshTokenExpiration
	) {
		byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
		key = Keys.hmacShaKeyFor(keyBytes);
		this.accessTokenExpiration = accessTokenExpiration;
		this.refreshTokenExpiration = refreshTokenExpiration;
	}

	public String generateAccessToken(String oauth2Key, String uuid) {
		return Jwts.builder()
			.id(uuid)
			.subject(oauth2Key)
			.issuedAt(new Date())
			.expiration(new Date((new Date()).getTime() + accessTokenExpiration))
			.signWith(key)
			.compact();
	}

	public String generateRefreshToken(String uuid) {
		return Jwts.builder()
			.id(uuid)
			.issuedAt(new Date())
			.expiration(new Date((new Date()).getTime() + refreshTokenExpiration))
			.signWith(key)
			.compact();
	}

	public String getUuid(String token) {
		return parseClaims(token).getId();
	}
	//
	// public String getOAuth2Key(String token) {
	// 	return parseClaims(token).getSubject();
	// }

	public Claims parseClaims(String token) {
		try {
			return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
		} catch (Exception e) {
			log.error("JWT Token parsing error: {}", e.getMessage());
			throw new CustomException(ErrorCode.INVALID_TOKEN, e.getMessage());
		}
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
			return true;
		} catch (Exception e) {
			log.error("JWT Token parsing error: {}", e.getMessage());
		}
		return false;
	}

	public static User getUser() {
		return (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}
