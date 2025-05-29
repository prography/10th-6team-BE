package com.prography.zone_2_be.global.exception

import com.prography.zone_2_be.global.error.ErrorCode
import com.prography.zone_2_be.global.response.ApiResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    private val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(ex: CustomException): ResponseEntity<ApiResponse<Void>> {
        log.error("CustomException: {}", ex.message, ex)
        return ApiResponse.error(ex.errorCode)
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneralException(ex: Exception): ResponseEntity<ApiResponse<Void>> {
        log.error("Exception: {}", ex.message, ex)
        return ApiResponse.error(ErrorCode.DEFAULT_ERROR)
    }
}