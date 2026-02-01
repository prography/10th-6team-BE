package com.prography.zone_2_be.domain.auth.exception;

import org.springframework.security.core.AuthenticationException;

import com.prography.zone_2_be.global.error.ErrorCode;

import lombok.Getter;

/**
 * 유효하지 않은 액세스 토큰 예외.
 * AuthenticationException을 상속받아 Spring Security 필터 체인에서
 * 인증 실패로 인지되도록 합니다.
 */
@Getter
public class InvalidTokenException extends AuthenticationException {

	private final ErrorCode errorCode;

	// 기본 생성자
	public InvalidTokenException() {
		super("유효하지 않은 토큰입니다."); // 부모 클래스에 에러 메시지 전달
		this.errorCode = ErrorCode.INVALID_TOKEN; // 기본 에러 코드 설정
	}

	// ErrorCode를 받는 생성자
	public InvalidTokenException(String message) {
		super(message); // 부모 클래스에 에러 메시지 전달
		this.errorCode = ErrorCode.INVALID_TOKEN;
	}

	public InvalidTokenException(ErrorCode errorCode, String message) {
		super(message); // 부모 클래스에 에러 메시지 전달
		this.errorCode = errorCode;
	}
}
