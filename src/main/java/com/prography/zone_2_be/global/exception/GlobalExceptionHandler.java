package com.prography.zone_2_be.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.prography.zone_2_be.global.error.ErrorCode;
import com.prography.zone_2_be.global.response.ApiResponse;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

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

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Void>> handleRequestParamException(
			MethodArgumentNotValidException ex) {

		List<ObjectError> errors = ex.getBindingResult().getAllErrors();

		String errorMessage = errors.stream()
				.map(ObjectError::getDefaultMessage)
				.collect(Collectors.joining(", "));
		log.error("MethodArgumentNotValidException: {}", ex.getMessage(), ex);
		return ApiResponse.error(ErrorCode.INVALID_REQUEST_PARAM, errorMessage);
	}

	@ExceptionHandler(AuthorizationDeniedException.class)
	public ResponseEntity<ApiResponse<Void>> handleAuthorizationDeniedException(
			AuthorizationDeniedException ex) {

		log.error("AuthorizationDeniedException: {}", ex.getMessage(), ex);
		return ApiResponse.error(ErrorCode.ACCESS_DENIED);
	}
}
