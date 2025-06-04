package com.prography.zone_2_be.global.config;

import com.prography.zone_2_be.domain.user.service.UserService;
import com.prography.zone_2_be.global.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
class SecurityConfiguration {
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req.
                        requestMatchers("/api/v1/health", "/api/v1/auth").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(
                        new JwtAuthFilter(jwtUtil, userService),
                        UsernamePasswordAuthenticationFilter.class
                );
        log.info("SecurityFilterChain Bean CREATED!");
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

}