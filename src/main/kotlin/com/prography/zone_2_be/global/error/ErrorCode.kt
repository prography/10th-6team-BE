package com.prography.zone_2_be.global.error

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val code: Int,
    val message: String
) {
    //global 에러
    DEFAULT_ERROR(
        HttpStatus.INTERNAL_SERVER_ERROR,
        5000,
        "서버에서 알 수 없는 오류 발생"
    ),
}