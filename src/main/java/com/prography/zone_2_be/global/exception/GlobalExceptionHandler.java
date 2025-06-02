package com.prography.zone_2_be.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.prography.zone_2_be.global.error.ErrorCode;
import com.prography.zone_2_be.global.response.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException ex) {
		log.error("CustomException: {}", ex.getMessage(), ex);
		return ApiResponse.error(ex.getErrorCode());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception ex) {
		log.error("Exception: {}", ex.getMessage(), ex);
		return ApiResponse.error(ErrorCode.DEFAULT_ERROR);
	}
}
