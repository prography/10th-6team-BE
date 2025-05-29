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
        4000,
        "현재 앱에 문제가 발생했으니 관리자에게 문의해주세요."
    ),
}