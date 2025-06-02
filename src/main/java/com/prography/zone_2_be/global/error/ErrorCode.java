package com.prography.zone_2_be.global.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	// global 에러
	DEFAULT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 5000, "서버에서 알 수 없는 오류 발생");

	private final HttpStatus status;
	private final int code;
	private final String message;
}
