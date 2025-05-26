package com.prography.zone_2_be.auth.controller


import com.prography.zone_2_be.auth.dto.AuthenticationRequest
import com.prography.zone_2_be.auth.dto.AuthenticationResponse
import com.prography.zone_2_be.auth.service.AuthService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authenticationService: AuthService
) {
    @PostMapping
    fun authenticate(
        @RequestBody authRequest: AuthenticationRequest
    ): AuthenticationResponse =
        authenticationService.authentication(authRequest)
}