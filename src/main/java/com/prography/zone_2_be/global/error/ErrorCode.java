package com.prography.zone_2_be.global.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	// Common Client Error 1000번대
	INVALID_REQUEST_PARAM(HttpStatus.BAD_REQUEST, 1000, "올바르지 않은 요청 파라미터 입니다."),
	ACCESS_DENIED(HttpStatus.FORBIDDEN, 1001, "접근 권한이 없습니다."),

	// User 에러 4000번대
	USER_NOT_FOUND(HttpStatus.BAD_REQUEST, 4000, "해당 유저를 찾을 수 없습니다."),

	// Notice 에러 4100번대
	NOTICE_NOT_FOUND(HttpStatus.NOT_FOUND, 4100, "해당 ID의 공지사항을 찾을 수 없습니다."),

	// Global Server 에러
	DEFAULT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 5000, "서버에서 알 수 없는 오류 발생.");

	private final HttpStatus status;
	private final int code;
	private final String message;
}
