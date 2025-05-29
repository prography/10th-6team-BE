package com.prography.zone_2_be.global.exception

import com.prography.zone_2_be.global.error.ErrorCode

class CustomException(
    val errorCode: ErrorCode
) : RuntimeException(errorCode.message) {
    val status = errorCode.status
    val code = errorCode
}