package com.prography.zone_2_be.domain.auth.controller;

import com.prography.zone_2_be.domain.auth.dto.AuthRequestDto;
import com.prography.zone_2_be.domain.auth.dto.AuthResponseDto;
import com.prography.zone_2_be.domain.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("")
    public ResponseEntity<AuthResponseDto> Auth(@Valid @RequestBody AuthRequestDto request) {
        String token = this.authService.createAccessToken(request);
        return ResponseEntity.status(HttpStatus.OK).body(new AuthResponseDto(token));
    }
}
