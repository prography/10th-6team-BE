package com.prography.zone_2_be.global.response;

import java.time.Instant;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.prography.zone_2_be.global.error.ErrorCode;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {

	@Builder.Default
	private final long timestamp = Instant.now().getEpochSecond();
	private final int code;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final T data;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String message;

	public static <T> ResponseEntity<ApiResponse<T>> success(T data) {
		ApiResponse<T> body = ApiResponse.<T>builder()
			.code(0)
			.data(data)
			.build();
		return ResponseEntity.ok(body);
	}

	public static ResponseEntity<ApiResponse<Void>> success() {
		ApiResponse<Void> body = ApiResponse.<Void>builder()
			.code(0)
			.build();
		return ResponseEntity.ok(body);
	}

	public static ResponseEntity<ApiResponse<Void>> error(ErrorCode errorCode) {
		ApiResponse<Void> body = ApiResponse.<Void>builder()
			.code(errorCode.getCode())
			.message(errorCode.getMessage())
			.build();
		return ResponseEntity.status(errorCode.getStatus())
			.body(body);
	}

	public static ResponseEntity<ApiResponse<Void>> error(ErrorCode errorCode,String message) {
		ApiResponse<Void> body = ApiResponse.<Void>builder()
				.code(errorCode.getCode())
				.message(errorCode.getMessage() + " " + message)
				.build();

		return ResponseEntity.status(errorCode.getStatus())
				.body(body);
	}
}
