import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.prography.zone_2_be.Zone2BeApplication
import com.prography.zone_2_be.auth.dto.AuthenticationRequest
import com.prography.zone_2_be.auth.dto.AuthenticationResponse
import com.prography.zone_2_be.util.JwtUtils
import io.jsonwebtoken.ExpiredJwtException
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.Test

@SpringBootTest(classes = [Zone2BeApplication::class])
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.yml")
class JwtApplicationIntegrationTest {
//    @Value("\${jwt.expiredToken}")
//    private lateinit var oldToken: String

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoSpyBean
    private lateinit var tokenService: JwtUtils

    @MockitoSpyBean
    private lateinit var userDetailsService: UserDetailsService


    @Test
    fun `access secured endpoint with new token from the refresh token after token expiration`() {
        val authRequest = AuthenticationRequest("email-1@gmail.com", "pass1")
        var jsonRequest = jacksonObjectMapper().writeValueAsString(authRequest)

        var response = mockMvc.perform(
            post("/api/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.accessToken").isNotEmpty)
            .andExpect(jsonPath("$.refreshToken").isNotEmpty).andReturn().response.contentAsString

        val authResponse = jacksonObjectMapper().readValue(response, AuthenticationResponse::class.java)

        // access secured endpoint
        mockMvc.perform(
            get("/api/hello")
                .header("Authorization", "Bearer ${authResponse.accessToken}")
        )
            .andExpect(status().isOk)
            .andExpect(content().string("Hello, Authorized User!"))

        // simulate access token expiration
        `when`(tokenService.extractUsername(authResponse.accessToken))
            .thenThrow(ExpiredJwtException::class.java)

        mockMvc.perform(
            get("/api/hello")
                .header("Authorization", "Bearer ${authResponse.accessToken}")
        )
            .andExpect(status().isForbidden)

        // create a new access token from the refresh token
//        val refreshTokenRequest = RefreshTokenRequest(authResponse.refreshToken)
//        jsonRequest = jacksonObjectMapper().writeValueAsString(refreshTokenRequest)
//
//        response = mockMvc.perform(
//            post("/api/auth/refresh")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(jsonRequest)
//        )
//            .andExpect(status().isOk)
//            .andExpect(jsonPath("$.token").isNotEmpty).andReturn().response.contentAsString
//
//        val newAccessToken = jacksonObjectMapper().readValue(response, TokenResponse::class.java)
//
//        reset(tokenService)
//
//        // access secured endpoint with the new token
//        mockMvc.perform(
//            get("/api/hello")
//                .header("Authorization", "Bearer ${newAccessToken.token}")
//        )
//            .andExpect(status().isOk)
//            .andExpect(content().string("Hello, Authorized User!"))
    }
}