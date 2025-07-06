package com.prography.zone_2_be.domain.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prography.zone_2_be.domain.auth.dto.TokenRefreshRequest;
import com.prography.zone_2_be.domain.auth.dto.TokenRefreshResponse;
import com.prography.zone_2_be.domain.auth.dto.UserAuthRequest;
import com.prography.zone_2_be.domain.auth.dto.UserAuthResponse;
import com.prography.zone_2_be.domain.auth.service.AuthService;
import com.prography.zone_2_be.global.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {

	private final AuthService authService;

	@PostMapping("")
	public ResponseEntity<ApiResponse<UserAuthResponse>> authUser(
		@Valid @RequestBody UserAuthRequest request,
		@RequestHeader(value = "authorization", required = true) String oauthToken) {
		return ApiResponse.success(authService.authorize(request, oauthToken));
	}

	@PostMapping("/refresh")
	public ResponseEntity<ApiResponse<TokenRefreshResponse>> refreshToken(
		@Valid @RequestBody TokenRefreshRequest request) {
		return ApiResponse.success(authService.refreshToken(request));
	}

	@PostMapping("/logout")
	public ResponseEntity<ApiResponse<Void>> logoutUser() {
		authService.logout();
		return ApiResponse.success();
	}
}
