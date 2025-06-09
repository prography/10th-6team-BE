package com.prography.zone_2_be.global.utils;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtil {
	private final SecretKey key;
	private final long accessTokenExpiration;
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

	public String getOAuth2Key(String token) {
		return parseClaims(token).getSubject();
	}

	public Claims parseClaims(String token) {
		return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
			return true;
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			log.info("Invalid JWT Token", e);
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT Token", e);
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT Token", e);
		} catch (IllegalArgumentException e) {
			log.info("JWT claims string is empty.", e);
		}
		return false;
	}

	public static String getUuid() {
		return (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}
