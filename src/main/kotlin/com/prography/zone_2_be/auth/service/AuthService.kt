package com.prography.zone_2_be.auth.service

import com.prography.zone_2_be.auth.dto.AuthenticationRequest
import com.prography.zone_2_be.auth.dto.AuthenticationResponse
import com.prography.zone_2_be.auth.repository.RefreshTokenRepository
import com.prography.zone_2_be.util.JwtUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.token.TokenService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.Date

@Service
class AuthService (private val authManager: AuthenticationManager,
                   private val userDetailsService: UserDetailsService,
                   private val refreshTokenRepository: RefreshTokenRepository,
                   private val tokenService: JwtUtils,
) {
    fun authentication(authenticationRequest: AuthenticationRequest): AuthenticationResponse {
        authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authenticationRequest.username,
                authenticationRequest.password
            )
        )

        val user = userDetailsService.loadUserByUsername(authenticationRequest.username)

        val accessToken = createAccessToken(user)
        val refreshToken = createRefreshToken(user)

        refreshTokenRepository.save(refreshToken, user)

        return AuthenticationResponse(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    fun refreshAccessToken(refreshToken: String): String {
        val username = tokenService.extractUsername(refreshToken)

        return username.let { user ->
            val currentUserDetails = userDetailsService.loadUserByUsername(user)
            val refreshTokenUserDetails = refreshTokenRepository.findUserDetailsByToken(refreshToken)

            if (currentUserDetails.username == refreshTokenUserDetails?.username)
                createAccessToken(currentUserDetails)
            else
                throw AuthenticationServiceException("Invalid refresh token")
        }
    }

    private fun createAccessToken(user: UserDetails): String {
        return tokenService.generateToken(
            subject = user.username,
        )
    }

    private fun createRefreshToken(user: UserDetails) = tokenService.generateToken(
        subject = user.username,
    )
}