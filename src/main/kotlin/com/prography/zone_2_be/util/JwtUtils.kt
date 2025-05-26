package com.prography.zone_2_be.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.Base64
import java.util.Date
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Component
class JwtUtils(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.expirationMs}") private val expirationMs: Long
) {

    private val signingKey: SecretKeySpec
        get() {
            println(secret)
            val keyBytes: ByteArray = Base64.getUrlDecoder().decode(secret)
            return SecretKeySpec(keyBytes, 0, keyBytes.size, "HmacSHA256")
        }

    fun generateToken(subject: String,  additionalClaims: Map<String, Any> = emptyMap()): String {
        return Jwts.builder()
            .claims(additionalClaims)
            .subject(subject)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + expirationMs))
            .signWith(signingKey)
            .compact()
    }

    fun extractUsername(token: String): String {
        return extractAllClaims(token).subject
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(signingKey)
            .build()
            .parseSignedClaims(token)
            .getPayload()
    }
}