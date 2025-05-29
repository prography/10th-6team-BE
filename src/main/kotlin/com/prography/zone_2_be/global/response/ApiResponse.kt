package com.prography.zone_2_be.global.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.prography.zone_2_be.global.error.ErrorCode
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import org.springframework.http.ResponseEntity
import java.time.Instant

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
class ApiResponse<T>(

    val timestamp: Long = Instant.now().toEpochMilli(),

    val code: Int,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    val data: T? = null,
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val message: String? = null,
) {
    companion object {
        fun success(): ResponseEntity<ApiResponse<Void>> =
            ResponseEntity.ok(ApiResponse(code = 0))

        fun <T> success(data: T): ResponseEntity<ApiResponse<T>> =
            ResponseEntity.ok(ApiResponse(code = 0, data = data))

        fun error(errorCode: ErrorCode): ResponseEntity<ApiResponse<Void>> =
            ResponseEntity.status(errorCode.status)
                .body(ApiResponse(code = errorCode.code, message = errorCode.message))
    }
}
