package com.prography.zone_2_be.global.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.prography.zone_2_be.domain.auth.exception.InvalidTokenException;
import com.prography.zone_2_be.domain.auth.exception.OAuth2LoadException;
import com.prography.zone_2_be.global.error.ErrorCode;
import com.prography.zone_2_be.global.response.ApiResponse;

import io.jsonwebtoken.JwtException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
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

	@ExceptionHandler({AuthorizationDeniedException.class, JwtException.class})
	public ResponseEntity<ApiResponse<Void>> handleAuthException(
		AuthorizationDeniedException ex) {

		log.error("Auth Exception: {}", ex.getMessage(), ex);
		return ApiResponse.error(ErrorCode.FORBIDDEN);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiResponse<Void>> handleConstraintViolation(ConstraintViolationException ex) {
		ConstraintViolation<?> violation = ex.getConstraintViolations().iterator().next();
		String errorMessage = violation.getMessage();

		log.error("ConstraintViolationException: {}", ex.getMessage(), ex);
		return ApiResponse.error(ErrorCode.INVALID_REQUEST_PARAM, errorMessage);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ApiResponse<Void>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
		String message = "올바른 파라미터 타입이 아닙니다";
		log.error("TypeMismatch on parameter '{}': {}", ex.getName(), ex.getMessage(), ex);

		return ApiResponse.error(ErrorCode.INVALID_REQUEST_PARAM, message);
	}

	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<ApiResponse<Void>> handleInvalidToken(InvalidTokenException ex) {
		log.error("Request token is invalid'{}': {}", ex.getErrorCode(), ex.getMessage(), ex);

		return ApiResponse.error(ex.getErrorCode(), ex.getMessage());
	}

	@ExceptionHandler(MissingRequestValueException.class)
	public ResponseEntity<ApiResponse<Void>> handleInvalidHeader(MissingRequestValueException ex) {
		log.error("request header is missing: {}", ex.getMessage(), ex);

		return ApiResponse.error(ErrorCode.MISSING_REQUIRED_VALUE, ex.getMessage());
	}
}
