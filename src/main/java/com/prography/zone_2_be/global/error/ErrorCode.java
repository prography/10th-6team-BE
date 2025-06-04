package com.prography.zone_2_be.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // global 에러
    DEFAULT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 5000, "서버에서 알 수 없는 오류 발생."),

    USER_NOT_FOUND_ERROR(HttpStatus.BAD_REQUEST, 4001, "해당 유저를 찾을 수 없습니다.");
    private final HttpStatus status;
    private final int code;
    private final String message;
}
