package com.prography.zone_2_be.global.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prography.zone_2_be.global.error.ErrorCode;
import com.prography.zone_2_be.global.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SecurityAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        // 사용할 에러 코드를 정의합니다. (인증 실패이므로 UNAUTHORIZED)
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        // 1. HTTP 응답 상태 코드 설정 (ErrorCode에서 가져옴)
        response.setStatus(errorCode.getStatus().value());

        // 2. 응답 컨텐츠 타입 설정
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        // 3. ApiResponse<Void> 객체 생성
        ResponseEntity<ApiResponse<Void>> body = ApiResponse.error(errorCode);

        // 4. ObjectMapper를 사용하여 ApiResponse 객체를 JSON 문자열로 변환
        String jsonResponse = objectMapper.writeValueAsString(body);

        // 5. 응답 본문에 JSON 작성
        response.getWriter().write(jsonResponse);
    }
}