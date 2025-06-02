package com.prography.zone_2_be.global.health;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prography.zone_2_be.global.response.ApiResponse;

@RestController
@RequestMapping("/api/v1")
public class HealthCheckController {

	@GetMapping("/health")
	public ResponseEntity<ApiResponse<Void>> health() {
		return ApiResponse.success();
	}
}
